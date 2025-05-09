package rfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rfp.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
