package rfp.service;

import jakarta.annotation.PostConstruct;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class SnippetSuggestionService {

    private String openaiApiKey;
    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void loadApiKeyFromXml() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.xml");
            if (input == null) {
                throw new IllegalStateException("config.xml not found in resources");
            }
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(input);
            this.openaiApiKey = doc.getElementsByTagName("key").item(0).getTextContent();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load API key from XML", e);
        }
    }

    public String adjustTone(String text, String desiredTone) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openaiApiKey);

            String prompt = "Rewrite the following in a " + desiredTone + " tone:\n" + text;

            Map<String, Object> body = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "system", "content", "You are a proposal writing assistant."),
                            Map.of("role", "user", "content", prompt)
                    )
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, Map.class);
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new RuntimeException("Invalid response from OpenAI: " + response.getStatusCode());
            }

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices == null || choices.isEmpty()) {
                throw new RuntimeException("No choices returned from OpenAI");
            }
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message != null ? (String) message.get("content") : "[No content returned]";

        } catch (Exception e) {
            return "[Error adjusting tone: " + e.getMessage() + "]";
        }
    }

    public List<String> suggestSnippets(String inputQuery) {
        return List.of("Sample snippet based on query: " + inputQuery);
    }
}
