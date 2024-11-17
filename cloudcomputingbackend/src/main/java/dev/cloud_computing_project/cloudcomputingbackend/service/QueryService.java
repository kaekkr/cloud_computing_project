package dev.cloud_computing_project.cloudcomputingbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DetectedLanguage;
import com.azure.core.credential.AzureKeyCredential;

@Service
public class QueryService {

	private final TextAnalyticsClient textAnalyticsClient;

	public QueryService(@Value("${azure.textanalytics.endpoint}") String endpoint,
			@Value("${azure.textanalytics.key}") String key) {
		this.textAnalyticsClient = new TextAnalyticsClientBuilder()
				.endpoint(endpoint)
				.credential(new AzureKeyCredential(key))
				.buildClient();
	}

	public String analyzeText(String userInput) {
		DetectedLanguage detectedLanguage = textAnalyticsClient.detectLanguage(userInput);
		return "Detected language: " + detectedLanguage.getName();
	}
}
