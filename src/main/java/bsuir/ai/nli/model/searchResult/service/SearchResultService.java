package bsuir.ai.nli.model.searchResult.service;

import bsuir.ai.nli.model.document.entity.Document;
import bsuir.ai.nli.model.searchResult.entity.SearchResult;

public interface SearchResultService {
    SearchResult createSearchResult(Document document, double rank, String query);
}
