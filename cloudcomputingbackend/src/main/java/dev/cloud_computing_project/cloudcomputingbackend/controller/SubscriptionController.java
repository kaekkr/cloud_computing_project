package dev.cloud_computing_project.cloudcomputingbackend.controller;

import dev.cloud_computing_project.cloudcomputingbackend.model.User;
import dev.cloud_computing_project.cloudcomputingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Subscribe the authenticated user
    @PostMapping("/subscribe")
    public String subscribe(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setHasSubscription(true);
        userRepository.save(user);

        return "Subscription activated for user: " + username;
    }

    // Unsubscribe the authenticated user
    @PostMapping("/unsubscribe")
    public String unsubscribe(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setHasSubscription(false);
        userRepository.save(user);

        return "Subscription deactivated for user: " + username;
    }
}
