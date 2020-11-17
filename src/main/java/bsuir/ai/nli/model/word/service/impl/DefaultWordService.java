package bsuir.ai.nli.model.word.service.impl;

import bsuir.ai.nli.model.word.entity.Word;
import bsuir.ai.nli.model.word.service.WordService;
import bsuir.ai.nli.model.word.repository.IgnoredWordRepository;
import bsuir.ai.nli.model.word.repository.WordRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultWordService implements WordService {
    @Resource
    private IgnoredWordRepository ignoredWordRepository;
    @Resource
    private WordRepository wordRepository;

    @Override
    public List<Word> getWordsFromTextExcerpt(String text) {
        List<String> wordsContents = parseIntoWords(text);

        return wordsContents.stream()
                .filter(wordsContent -> !wordsContent.isEmpty())
                .filter(wordsContent -> !isIgnoredWord(wordsContent))
                .map(wordsContent -> Word.builder().content(wordsContent).build()).collect(Collectors.toList());
    }

    @Override
    public void addAll(List<Word> words) {
        words.stream()
                .filter(word -> !isWordExist(word))
                .peek(wordRepository::save).collect(Collectors.toList());
    }

    @Override
    public Map<Word, Float> getWordsFrequency(List<Word> words) {
        Map<Word, Integer> wordsRepetitions = new HashMap<>();
        words.forEach(word -> {
            int quantityOfWordRepetitions = wordsRepetitions.containsKey(word) ? wordsRepetitions.get(word) + 1 : 1;
            wordsRepetitions.put(wordRepository.findByContent(word.getContent()).get(), quantityOfWordRepetitions);
        });
        return wordsRepetitions.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (float) entry.getValue()/words.size()));
    }

    @Override
    public Set<Word> extractWordsFromQuery(String query) {
        Set<Word> words = new HashSet<>();
        Arrays.stream(query.split(" "))
                .forEach(queryWord ->  wordRepository.findByContent(queryWord)
                        .ifPresent(words::add));
        return words;
    }

    private List<String> parseIntoWords(String text) {
        return Arrays.stream(text.split("[ |\n]"))
                .map(wordContent -> wordContent.replaceAll("[–—”“_*…,.!?:;'`/|()\\[\\]]", ""))
                .collect(Collectors.toList());
    }

    private boolean isWordExist(Word word) {
        return wordRepository.findByContent(word.getContent()).isPresent();
    }

    private boolean isIgnoredWord(String wordContent) {
        return ignoredWordRepository.findByContent(wordContent).isPresent();
    }
}
