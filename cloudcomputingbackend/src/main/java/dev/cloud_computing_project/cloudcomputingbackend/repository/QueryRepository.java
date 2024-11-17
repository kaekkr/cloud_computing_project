package dev.cloud_computing_project.cloudcomputingbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cloud_computing_project.cloudcomputingbackend.model.Query;

public interface QueryRepository extends JpaRepository<Query, Long> {

}
