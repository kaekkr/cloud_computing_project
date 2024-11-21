package dev.cloud_computing_project.cloudcomputingbackend.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {

    private final String apiKey = "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";

    public String getHealthcareAdvice(String userInput) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key is missing. Please set the 'OPENAI_API_KEY_CLOUD_COMPUTING' environment variable.");
        }

        OpenAiService service = new OpenAiService(apiKey);

        String prompt = "A user describes their symptoms as follows: \"" + userInput +
                "\". Based on this information, provide general healthcare advice. " +
                "Avoid diagnosing conditions and suggest seeing a healthcare provider if necessary.";

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-4") // Ensure you're using GPT-4
                .prompt(prompt)
                .maxTokens(300) // Adjust token limit as needed
                .temperature(0.7) // Adjust creativity level
                .build();

        try {
            CompletionResult result = service.createCompletion(request);
            return result.getChoices().getFirst().getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I couldn't process your request. Please try again later.";
        }
    }
}
