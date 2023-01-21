package com.accenture.paymentModule.service;

import com.accenture.paymentModule.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> findAll();
    public User findById(Long id);
    public User findByFirstNameIgnoreCase(String firstName);
    public User findByLastNameIgnoreCase(String lastName);

}
