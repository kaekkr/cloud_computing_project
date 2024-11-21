package dev.cloud_computing_project.cloudcomputingbackend.service;

import org.springframework.beans.factory.annotation.Value;
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

    public String analyzeIntent(String userInput) {
        String url = String.format("%s/language/:analyze-conversations?projectName=%s&deploymentName=%s&api-version=2022-10-01-preview",
                endpoint, projectName, deploymentName);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Ocp-Apim-Subscription-Key", apiKey);

        String payload = String.format(
                "{\"analysisInput\": {\"conversationItem\": {\"id\": \"1\", \"participantId\": \"user\", \"text\": \"%s\"}}, \"parameters\": {\"projectName\": \"%s\", \"deploymentName\": \"%s\"}}",
                userInput, projectName, deploymentName
        );

        HttpEntity<String> request = new HttpEntity<>(payload, headers);
        return restTemplate.postForObject(url, request, String.class);
    }
}
