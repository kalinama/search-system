package bsuir.ai.nli.model.word.repository;

import bsuir.ai.nli.model.word.entity.IgnoredWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IgnoredWordRepository extends JpaRepository<IgnoredWord, Long> {
    Optional<IgnoredWord> findByContent(String content);
}
