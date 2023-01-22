package com.accenture.paymentModule.repository;

import com.accenture.paymentModule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstNameIgnoreCase(String firstName);
    Optional<User> findByLastNameIgnoreCase(String lastName);
    Optional<User> findByDni(String dni);

}
