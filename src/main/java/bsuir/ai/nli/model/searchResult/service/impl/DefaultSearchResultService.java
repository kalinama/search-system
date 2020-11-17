package bsuir.ai.nli.model.searchResult.service.impl;

import bsuir.ai.nli.model.document.entity.Document;
import bsuir.ai.nli.model.searchResult.entity.SearchResult;
import bsuir.ai.nli.model.searchResult.service.SearchResultService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DefaultSearchResultService implements SearchResultService {

    public SearchResult createSearchResult(Document document, double rank, String query) {
        return SearchResult.builder()
                .document(document)
                .rank(rank)
                .snippet(createSnippet(query, document.getText()))
                .date(LocalDateTime.now())
                .build();
    }

    private String createSnippet(String query, String documentText) {
        Set<String> queryWords =new HashSet<>(Arrays. asList(query.split(" ")));
        StringBuilder snippet = new StringBuilder();

        for (String word : queryWords) {
            String text = "";
            if(snippet.indexOf(word) == -1) {
                Pattern pattern = Pattern.compile("([,.!?]|^)\\s?[A-ZА-Я][^,.!?]+\\s" + word + "\\s[^,.!?]*[,.!?]");
                Matcher matcher = pattern.matcher(documentText);
                if(matcher.find())
                    text = matcher.group(0);
                snippet.append("...");
                snippet.append(text.replaceAll(word, "<b>" + word + "</b>")
                        .replaceFirst("\\.\\s", ""));
            } else {
                text = snippet.toString();
                snippet = new StringBuilder(text.replaceAll(word, "<b>" + word + "</b>"));
            }
        }
        return snippet.append("...").toString();
    }
}
