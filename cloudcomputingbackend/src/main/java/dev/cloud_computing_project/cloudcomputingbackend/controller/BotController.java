package dev.cloud_computing_project.cloudcomputingbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cloud_computing_project.cloudcomputingbackend.repository.UserRepository;
import dev.cloud_computing_project.cloudcomputingbackend.service.ChatGPTService;
import dev.cloud_computing_project.cloudcomputingbackend.service.CluService;
import dev.cloud_computing_project.cloudcomputingbackend.service.TextAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bot")
@RequiredArgsConstructor
public class BotController {

    private final UserRepository userRepository;
    private final CluService cluService;
    private final TextAnalyticsService textAnalyticsService;
    private final ChatGPTService chatGPTService;

    // Handle user messages
    @PostMapping("/message")
    public String handleMessage(@RequestBody String userInput, Authentication authentication) {
        // Check if the authentication is not null
        if (authentication == null) {
            return "Authentication failed. Please log in.";
        }

        String username = authentication.getName();
        boolean hasSubscription = userRepository.existsByUsernameAndHasSubscriptionTrue(username);

        if (!hasSubscription) {
            return "You need an active subscription to use the bot. Please subscribe.";
        }

        try {
            // Process the user input with the bot logic (e.g., LUIS, ChatGPT)
            String intentResponse = cluService.analyzeIntent(userInput);
            String language = textAnalyticsService.detectLanguage(userInput);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(intentResponse);

            String topIntent = responseNode.at("/result/prediction/topIntent").asText();
            double confidence = responseNode.at("/result/prediction/intents/0/confidenceScore").asDouble();

            // Handle intents based on the topIntent
            if ("Greetings".equals(topIntent) && confidence > 0.6) {
                return "Hello! How can I assist you today?";
            } else if ("GetHealthcareAdvice".equals(topIntent) && confidence > 0.6) {
                // Call ChatGPT to get healthcare advice
                String healthcareAdvice = chatGPTService.getHealthcareAdvice(userInput);
                return "Healthcare Advice: " + healthcareAdvice;
            } else {
                return "I'm not sure how to respond to that. Can you clarify?";
            }
        } catch (Exception e) {
            // Handle any potential errors gracefully
            return "An error occurred while processing your message. Please try again later.";
        }
    }
}
