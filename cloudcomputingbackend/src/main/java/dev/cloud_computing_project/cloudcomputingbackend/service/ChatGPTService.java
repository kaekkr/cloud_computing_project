package dev.cloud_computing_project.cloudcomputingbackend.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChatGPTService {

    private final String apiKey = "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";
    private final OpenAiService openAiService;

    public ChatGPTService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // Connection timeout
                .readTimeout(60, TimeUnit.SECONDS)    // Read timeout
                .writeTimeout(60, TimeUnit.SECONDS)   // Write timeout
                .build();

        // Create an OpenAiService instance with the custom client
        this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(60)) {
            private OkHttpClient.Builder defaultClient() {
                return client.newBuilder();
            }
        };
    }

    public String getHealthcareAdvice(String userInput) {
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
            return this.openAiService.createChatCompletion(chatRequest)
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
