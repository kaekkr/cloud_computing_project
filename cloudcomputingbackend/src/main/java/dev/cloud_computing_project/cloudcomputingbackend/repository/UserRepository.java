package dev.cloud_computing_project.cloudcomputingbackend.repository;

import dev.cloud_computing_project.cloudcomputingbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsernameAndHasSubscriptionTrue(String username);
}
