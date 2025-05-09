/*package rfp.rag.factories;

import com.theokanning.openai.completion.chat.*;

import java.util.List;

public class OpenAiChatOptionsFactory {

    public static ChatCompletionRequest fromPrompt(String prompt) {
        ChatMessage message = new ChatMessage("user", prompt);
        return ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(message))
                .build();
    }
}*/