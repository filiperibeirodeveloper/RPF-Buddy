package rfp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rfp.model.Proposal;
import rfp.service.ProposalService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PostMapping("/upload")
    public ResponseEntity<Proposal> uploadProposal(
            @RequestParam String title,
            @RequestParam String client,
            @RequestParam String tags,
            @RequestParam String content,
            @RequestParam String outcome,
            @RequestParam MultipartFile file
    ) throws IOException {
        Proposal saved = proposalService.saveProposal(title, client, tags, content, outcome, file);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Proposal>> getAllProposals() {
        return ResponseEntity.ok(proposalService.getAllProposals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposalById(@PathVariable Long id) {
        return ResponseEntity.ok(proposalService.getProposal(id));
    }
}