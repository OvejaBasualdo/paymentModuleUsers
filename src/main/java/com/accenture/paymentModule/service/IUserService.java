package com.accenture.paymentModule.service;

import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.entity.ResultsType;
import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.models.Account;

import java.util.List;

public interface IUserService {
    public List<User> findAll();
    public User findById(Long id);
    public User findByFirstNameIgnoreCase(String firstName);
    public User findByLastNameIgnoreCase(String lastName);
    public User findByDni (String dni);
    public Long findByAccountId(Long accountId);
    public List<Account> findAccountFromUserId(Long userId);
    public ResultsType createUser(UserDTO userDTO);
}
