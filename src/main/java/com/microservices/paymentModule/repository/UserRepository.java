package com.microservices.paymentModule.repository;

import com.microservices.paymentModule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstNameIgnoreCase(String firstName);
    Optional<User> findByLastNameIgnoreCase(String lastName);

    Optional<User> findByIdAndIsActiveTrue(Long id);

    Optional<User> findByDni(String dni);
    List<User> findByIsActiveTrue();

}
