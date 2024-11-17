package dev.cloud_computing_project.cloudcomputingbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cloud_computing_project.cloudcomputingbackend.service.QueryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/queries")
@RequiredArgsConstructor
public class QueryController {

	private final QueryService queryService;

	@PostMapping
	public String handleQuery(@RequestBody String userInput) {
		return queryService.analyzeText(userInput);
	}
}
