package bsuir.ai.nli.model.document.repository;

import bsuir.ai.nli.model.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByTextContains(String word);
}
