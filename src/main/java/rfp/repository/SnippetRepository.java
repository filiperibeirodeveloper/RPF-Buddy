package rfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rfp.model.Snippet;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
}
