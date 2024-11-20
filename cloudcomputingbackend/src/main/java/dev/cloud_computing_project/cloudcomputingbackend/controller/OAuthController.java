package dev.cloud_computing_project.cloudcomputingbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class OAuthController {

    @GetMapping("/success")
    public String loginSuccess() {
        return "OAuth2 Login Successful!";
    }

    @GetMapping("/failure")
    public String loginFailure() {
        return "OAuth2 Login Failed!";
    }
}
