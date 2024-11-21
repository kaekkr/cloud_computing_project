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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bot")
@RequiredArgsConstructor
public class BotController {

    private final UserRepository userRepository;
    private final CluService cluService;
    private final TextAnalyticsService textAnalyticsService;
    private final ChatGPTService chatGPTService;

    @PostMapping("/message")
    public Map<String, Object> handleMessage(@RequestBody String userInput, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        // Check if the authentication is not null
        if (authentication == null) {
            response.put("status", "error");
            response.put("message", "Authentication failed. Please log in.");
            return response;
        }

        String username = authentication.getName();
        boolean hasSubscription = userRepository.existsByUsernameAndHasSubscriptionTrue(username);

        if (!hasSubscription) {
            response.put("status", "error");
            response.put("message", "You need an active subscription to use the bot. Please subscribe.");
            return response;
        }

        try {
            // Process the user input
            String intentResponse = cluService.analyzeIntent(userInput);
            String language = textAnalyticsService.detectLanguage(userInput);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(intentResponse);

            String topIntent = responseNode.at("/result/prediction/topIntent").asText();
            double confidence = responseNode.at("/result/prediction/intents/0/confidenceScore").asDouble();

            // Handle intents based on the topIntent
            if ("Greetings".equals(topIntent) && confidence > 0.6) {
                response.put("status", "success");
                response.put("response", "Hello! How can I assist you today?");
            } else if ("GetHealthcareAdvice".equals(topIntent) && confidence > 0.6) {
                String healthcareAdvice = chatGPTService.getHealthcareAdvice(userInput);
                response.put("status", "success");
                response.put("response", "Healthcare Advice: " + healthcareAdvice);
            } else {
                response.put("status", "error");
                response.put("response", "I'm not sure how to respond to that. Can you clarify?");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred while processing your message. Please try again later.");
        }

        return response;
    }
}
