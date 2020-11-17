package bsuir.ai.nli.model.word.service;

import bsuir.ai.nli.model.word.entity.Word;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WordService {
    List<Word> getWordsFromTextExcerpt(String text);
    void addAll(List<Word> words);
    Map<Word, Float> getWordsFrequency(List<Word> words);
    Set<Word> extractWordsFromQuery(String query);
}
