package rfp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rfp.model.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
}
