package rfp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rfp.model.Proposal;
import rfp.repository.ProposalRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    public Proposal saveProposal(String title, String client, String tags, String content,
                                 String outcome, MultipartFile file) throws IOException {
        Proposal proposal = new Proposal();
        proposal.setTitle(title);
        proposal.setClient(client);
        proposal.setDate(LocalDate.now());
        proposal.setTags(tags);
        proposal.setContent(content);
        proposal.setOutcome(outcome);

        if (!file.isEmpty()) {
            String uploadDir = "uploads/" + System.currentTimeMillis();
            File dir = new File(uploadDir);
            dir.mkdirs();
            String path = uploadDir + "/" + file.getOriginalFilename();
            file.transferTo(new File(path));
            proposal.setFilePath(path);
        }

        return proposalRepository.save(proposal);
    }

    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    public Proposal getProposal(Long id) {
        return proposalRepository.findById(id).orElse(null);
    }
}