package rfp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rfp.model.Snippet;
import rfp.repository.SnippetRepository;

import java.util.List;

@Service
public class SnippetService {

    @Autowired
    private SnippetRepository snippetRepository;

    public List<Snippet> getAllSnippets() {
        return snippetRepository.findAll();
    }

    public Snippet saveSnippet(Snippet snippet) {
        return snippetRepository.save(snippet);
    }
}