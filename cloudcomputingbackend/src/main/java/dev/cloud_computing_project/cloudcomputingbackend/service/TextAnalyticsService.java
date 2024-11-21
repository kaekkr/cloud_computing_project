package dev.cloud_computing_project.cloudcomputingbackend.service;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DetectedLanguage;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TextAnalyticsService {

    private final TextAnalyticsClient client;

    public TextAnalyticsService(@Value("${azure.textanalytics.endpoint}") String endpoint,
                                @Value("${azure.textanalytics.key}") String key) {
        this.client = new TextAnalyticsClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(key))
                .buildClient();
    }

    public String detectLanguage(String text) {
        DetectedLanguage language = client.detectLanguage(text);
        return "Detected language: " + language.getName();
    }
}
