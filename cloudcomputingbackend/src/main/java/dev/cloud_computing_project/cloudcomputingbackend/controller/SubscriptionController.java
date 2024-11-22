package dev.cloud_computing_project.cloudcomputingbackend.controller;

import dev.cloud_computing_project.cloudcomputingbackend.model.User;
import dev.cloud_computing_project.cloudcomputingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
@CrossOrigin
public class SubscriptionController {

    private final UserRepository userRepository;

    @PostMapping("/subscribe")
    public Map<String, Object> subscribe(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        String username = authentication.getName();

        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            user.setHasSubscription(true);
            userRepository.save(user);

            response.put("status", "success");
            response.put("message", "Subscription activated for user: " + username);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

    @PostMapping("/unsubscribe")
    public Map<String, Object> unsubscribe(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        String username = authentication.getName();

        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            user.setHasSubscription(false);
            userRepository.save(user);

            response.put("status", "success");
            response.put("message", "Subscription deactivated for user: " + username);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }
}
