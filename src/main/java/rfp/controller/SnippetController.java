package rfp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rfp.model.Snippet;
import rfp.service.SnippetService;

import java.util.List;

@RestController
@RequestMapping("/api/snippets")
public class SnippetController {

    @Autowired
    private SnippetService snippetService;

    @GetMapping
    public ResponseEntity<List<Snippet>> getAllSnippets() {
        return ResponseEntity.ok(snippetService.getAllSnippets());
    }

    @PostMapping
    public ResponseEntity<Snippet> saveSnippet(@RequestBody Snippet snippet) {
        return ResponseEntity.ok(snippetService.saveSnippet(snippet));
    }
}
