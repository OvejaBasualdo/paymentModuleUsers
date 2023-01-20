package com.accenture.paymentModule.repository;

import com.accenture.paymentModule.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
