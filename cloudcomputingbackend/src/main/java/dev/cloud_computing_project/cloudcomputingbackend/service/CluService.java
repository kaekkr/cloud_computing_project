package dev.cloud_computing_project.cloudcomputingbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Service
public class CluService {

    @Value("${azure.clu.endpoint}")
    private String endpoint;

    @Value("${azure.clu.key}")
    private String apiKey;

    @Value("${azure.clu.project-name}")
    private String projectName;

    @Value("${azure.clu.deployment-name}")
    private String deploymentName;

    private static final String API_VERSION = "2023-04-01"; // Replace with the appropriate API version

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Analyze user input using Azure CLU
    public String analyzeIntent(String userInputString) {
        try {
            // Parse the string as a JSON
            JsonNode requestJson = objectMapper.readTree(userInputString);

            // Extract userInput value
            String userInput = requestJson.get("userInput").asText();

            String url = String.format("%s/language/:analyze-conversations?api-version=%s&projectName=%s&deploymentName=%s",
                    endpoint, API_VERSION, projectName, deploymentName);

            // Create RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Prepare HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Ocp-Apim-Subscription-Key", apiKey);

            // Prepare JSON payload for the API
            String payload = String.format(
                    "{\"kind\": \"Conversation\", \"analysisInput\": {\"conversationItem\": {\"id\": \"1\", \"participantId\": \"user\", \"text\": \"%s\"}}, \"parameters\": {\"projectName\": \"%s\", \"deploymentName\": \"%s\", \"stringIndexType\": \"TextElement_V8\"}}",
                    userInput, projectName, deploymentName
            );

            HttpEntity<String> request = new HttpEntity<>(payload, headers);

            System.out.println(request.toString());
            System.out.println(url);

            // Send the request and return the response
            String response = restTemplate.postForObject(url, request, String.class);

            // Debug: log the response
            System.out.println("Response: " + response);

            return response;
        } catch (Exception e) {
            // Log the exception to help debug
            e.printStackTrace();
            return "error";
        }
    }
}
