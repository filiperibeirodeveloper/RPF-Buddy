package rfp.rag.service;

import jakarta.annotation.PostConstruct;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import rfp.rag.persistence.VectorStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentSeeder {

    @Autowired
    private VectorStore vectorStore;

    private final Tika tika = new Tika();

    @PostConstruct
    public void seed() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:/ai/rag/*");

            int totalChunks = 0;
            int totalFiles = 0;

            for (Resource resource : resources) {
                if (!resource.exists() || !resource.isReadable()) continue;

                try (InputStream input = resource.getInputStream()) {
                    String text = tika.parseToString(input);
                    List<String> chunks = splitIntoChunks(text, 1000);
                    chunks.forEach(vectorStore::add);
                    totalChunks += chunks.size();
                    totalFiles++;
                    System.out.println("✅ Loaded " + chunks.size() + " chunks from: " + resource.getFilename());
                } catch (IOException | TikaException e) {
                    System.err.println("⚠️ Failed to read " + resource.getFilename() + ": " + e.getMessage());
                }
            }

            System.out.println("✅ DocumentSeeder loaded " + totalChunks + " chunks from " + totalFiles + " files.");

        } catch (IOException e) {
            System.err.println("❌ Failed to scan resource directory: " + e.getMessage());
        }
    }

    private List<String> splitIntoChunks(String text, int maxChunkSize) {
        List<String> chunks = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");
        StringBuilder current = new StringBuilder();
        for (String line : lines) {
            if (current.length() + line.length() > maxChunkSize) {
                chunks.add(current.toString());
                current = new StringBuilder();
            }
            current.append(line).append("\n");
        }
        if (!current.isEmpty()) {
            chunks.add(current.toString());
        }
        return chunks;
    }
}
