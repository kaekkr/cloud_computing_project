package dev.cloud_computing_project.cloudcomputingbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
public class Query {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userInput;
	private String botResponse;

	public Query() {
		this.userInput = "";
		this.botResponse = "Request is empty";
	}

	public Query(String userInput){
		this.userInput = userInput;

		String apiKey = System.getenv("OPENAI_API_KEY_CLOUD_COMPUTING");
		if (apiKey == null || apiKey.isEmpty()) {
			System.err.println("API key is missing. Set the 'OPENAI_API_KEY' environment variable.");
		} else {
			System.out.println("API Key retrieved successfully.");
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
			// Get the response
			CompletionResult result = service.createCompletion(request);

			// Print the healthcare advice and save to botResponse
			String response = result. getChoices(). get(0). getText();
			System.out.println("Healthcare Advice:");
			System.out.println(response);

			this.botResponse = response;
		} catch (Exception e) {
			System.err.println("Error occurred: " + e.getMessage());
		}
	}

}
