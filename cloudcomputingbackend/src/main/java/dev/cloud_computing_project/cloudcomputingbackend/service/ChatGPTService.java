package dev.cloud_computing_project.cloudcomputingbackend.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGPTService {

    private final String apiKey = "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";

    public String getHealthcareAdvice(String userInput) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key is missing. Please set the 'OPENAI_API_KEY_CLOUD_COMPUTING' environment variable.");
        }

        OpenAiService service = new OpenAiService(apiKey);

        // Create the system prompt and user input messages
        ChatMessage systemMessage = new ChatMessage("system", "You are a helpful healthcare assistant. Provide general healthcare advice based on user symptoms without diagnosing conditions. Encourage consulting a healthcare provider when necessary.");
        ChatMessage userMessage = new ChatMessage("user", userInput);

        // Build the chat completion request
        ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                .model("gpt-4") // Use a supported chat model
                .messages(List.of(systemMessage, userMessage))
                .maxTokens(300) // Adjust token limit as needed
                .temperature(0.7) // Adjust creativity level
                .build();

        try {
            // Send the request to OpenAI and get the response
            return service.createChatCompletion(chatRequest)
                    .getChoices()
                    .getFirst()
                    .getMessage()
                    .getContent()
                    .trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I couldn't process your request. Please try again later.";
        }
    }
}
