package rfp.rag.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import rfp.rag.persistence.VectorStore;

@Service
public class AiService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ResourceLoader resourceLoader;

    private final VectorStore vectorStore = new VectorStore();

    public String generateReply(String query) {
        // 1. Embed query and retrieve relevant context (mocked or vector logic)
        List<String> contextChunks = vectorStore.searchSimilar(query, 3);
        String context = String.join("\n", contextChunks);

        // 2. Load the .st prompt template from resources
        String templateText = loadPromptTemplate("classpath:ai/templates/rag-prompt-template.st");

        // 3. Apply Spring AI PromptTemplate
        PromptTemplate template = new PromptTemplate(templateText);
        Prompt prompt = template.create(Map.of("input", query, "documents", context));

        // 4. Ask OpenAI via Spring AI
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    private String loadPromptTemplate(String path) {
        try {
            Resource resource = resourceLoader.getResource(path);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prompt template", e);
        }
    }
}
