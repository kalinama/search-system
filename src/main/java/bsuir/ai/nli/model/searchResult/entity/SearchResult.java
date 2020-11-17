package bsuir.ai.nli.model.searchResult.entity;

import bsuir.ai.nli.model.document.entity.Document;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SearchResult {
    private Document document;

    private double rank;

    private LocalDateTime date;

    private String snippet;
}
