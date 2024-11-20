package dev.cloud_computing_project.cloudcomputingbackend.controller;

import dev.cloud_computing_project.cloudcomputingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bot")
@RequiredArgsConstructor
public class BotController {

    private final UserRepository userRepository;

    @PostMapping("/message")
    public String handleMessage(@RequestBody String userInput, Authentication authentication) {
        String username = authentication.getName();
        boolean hasSubscription = userRepository.existsByUsernameAndHasSubscriptionTrue(username);

        if (!hasSubscription) {
            return "You need an active subscription to use the bot. Please subscribe.";
        }

        // Process the user input with the bot logic (e.g., LUIS, ChatGPT)
        return "Bot response for: " + userInput;
    }
}
