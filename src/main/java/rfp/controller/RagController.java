package rfp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import rfp.*;
import rfp.rag.service.AiService;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/api/rag")
public class RagController {

    @Autowired
    private AiService aiService;

    @PostMapping("/reply")
    public Map<String, String> generateReply(@RequestBody Map<String, String> body) {
        String query = body.get("query");
        String answer = aiService.generateReply(query);
        return Map.of("output", answer, "input", query);
    }
}