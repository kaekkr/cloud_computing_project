package dev.cloud_computing_project.cloudcomputingbackend;

import dev.cloud_computing_project.cloudcomputingbackend.model.User;
import dev.cloud_computing_project.cloudcomputingbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("user1").isEmpty()) {
            User user = new User(null, "user1", passwordEncoder.encode("password1"), "USER", false);
            userRepository.save(user);
        }

        if (userRepository.findByUsername("user2").isEmpty()) {
            User user = new User(null, "user2", passwordEncoder.encode("password2"), "USER", true);
            userRepository.save(user);
        }
    }
}
