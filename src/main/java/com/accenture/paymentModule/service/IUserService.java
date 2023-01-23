package com.accenture.paymentModule.service;

import com.accenture.paymentModule.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
    public List<User> findAll();
    public User findById(Long id);
    public User findByFirstNameIgnoreCase(String firstName);
    public User findByLastNameIgnoreCase(String lastName);
    public User findByDni (String dni);
    public Long findByAccountId(Long accountId);

}
