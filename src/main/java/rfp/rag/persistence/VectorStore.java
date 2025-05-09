package rfp.rag.persistence;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class VectorStore {
    private static final List<VectorEntry> store = new ArrayList<>();

    public void add(String text) {
        store.add(new VectorEntry(text));
    }

    public List<String> searchSimilar(String query, int topN) {
        return store.stream()
                .sorted(Comparator.comparingInt(e -> -similarityScore(e.text(), query)))
                .limit(topN)
                .map(VectorEntry::text)
                .toList();
    }

    private int similarityScore(String text, String query) {
        int score = 0;
        for (String word : query.toLowerCase().split("\\s+")) {
            if (text.toLowerCase().contains(word)) {
                score++;
            }
        }
        return score;
    }

    private record VectorEntry(String text) {}
}
