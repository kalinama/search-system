package bsuir.ai.nli.model.document.service;

import bsuir.ai.nli.model.document.entity.Document;
import bsuir.ai.nli.model.searchResult.entity.SearchResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    void addAll(List<Document> documents);

    Document getById(long id);

    Document createDocument(MultipartFile file) throws IOException;

    List<Document> createDocument(MultipartFile[] files) throws IOException;
    List<SearchResult> search(String query);
}
