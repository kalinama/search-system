package bsuir.ai.nli.model.document.service.impl;

import bsuir.ai.nli.model.document.entity.Document;
import bsuir.ai.nli.model.searchResult.entity.SearchResult;
import bsuir.ai.nli.model.word.entity.Word;
import bsuir.ai.nli.model.document.service.DocumentService;
import bsuir.ai.nli.model.document.repository.DocumentRepository;
import bsuir.ai.nli.model.searchResult.service.SearchResultService;
import bsuir.ai.nli.model.word.service.WordService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultDocumentService implements DocumentService {
    @Resource
    private DocumentRepository documentRepository;

    @Resource(name = "defaultWordService")
    private WordService wordService;

    @Resource(name = "defaultSearchResultService")
    private SearchResultService searchResultService;

    @Override
    public void addAll(List<Document> documents) {
        documents.addAll(documentRepository.findAll());
        for (Document document : documents) {
            List<Word> allNewWords = wordService.getWordsFromTextExcerpt(document.getText());
            if (document.getId() == null) {
                wordService.addAll(allNewWords.stream()
                        .distinct()
                        .collect(Collectors.toList()));
            }
            document.setWordsFrequency(wordService.getWordsFrequency(allNewWords));
        }
        recalculateWordsWeights(documents);
        documentRepository.saveAll(documents);
    }

    @Override
    public Document getById(long id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public Document createDocument(MultipartFile file) throws IOException {
        String text = new String(file.getBytes());
        String title = file.getOriginalFilename();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        return Document.builder()
                .title(title)
                .text(text)
                .date(date)
                .time(time)
                .build();
    }

    @Override
    public List<Document> createDocument(MultipartFile[] files) throws IOException {
        List<Document> documents = new ArrayList<>();
        for (MultipartFile file: files) {
            if (!file.isEmpty()) {
                documents.add(createDocument(file));
            }
        }
        return documents;
    }

    private void recalculateWordsWeights(List<Document> documents) {
        long quantityOfDocuments = documents.size();

        for (Document document : documents) {
            Map<Word, Float> wordsWeights = new HashMap<>();
            document.getWordsFrequency().forEach((word, wordFrequency) -> {
                long quantityOfDocumentsWithSuchWord = documents.stream()
                        .filter(doc -> doc.getWordsFrequency().containsKey(word))
                        .count();
                float weight = (float) (wordFrequency * Math.log((float) quantityOfDocuments / quantityOfDocumentsWithSuchWord));
                wordsWeights.put(word, weight);
            });
            document.setWordsWeights(wordsWeights);
        }
    }

    @Override
    public  List<SearchResult> search(String query) {
        Set<Word> queryWords = wordService.extractWordsFromQuery(query);
        if (queryWords.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Document> documentsByQuery = getDocumentsWithWords(queryWords);
        List<SearchResult> searchResults = new ArrayList<>();
        for (Document document : documentsByQuery) {
            double rank = calculateDocumentRank(queryWords, document);
            if (rank == 0) continue;
            SearchResult searchResult = searchResultService.createSearchResult(document, rank, query);
            searchResults.add(searchResult);
        }
        searchResults.sort(Comparator.comparing(SearchResult::getRank).reversed());
        return searchResults;
    }

    private double calculateDocumentRank(Set<Word> queryWords, Document document) {
        double rank = 0;
        for (Word queryWord : queryWords) {
            rank += Optional.ofNullable(document.getWordsWeights()
                    .get(queryWord))
                    .orElse(0f);
        }
        return rank;
    }

    private  Set<Document> getDocumentsWithWords(Set<Word> words) {
        Set<Document> documents = new HashSet<>();
        words.forEach(word -> documents.addAll(documentRepository.findAllByTextContains(word.getContent())));
        return documents;
    }


}
