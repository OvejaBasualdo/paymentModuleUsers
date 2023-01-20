package com.accenture.paymentModule.service;

import com.accenture.paymentModule.models.User;

import java.util.List;

public interface IUserService {
    public List<User> findAll();
    public User findById(Long id);
}
