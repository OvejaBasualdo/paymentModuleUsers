package com.microservices.paymentModule.service;

import com.microservices.paymentModule.dto.UserDTO;
import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.models.Account;

import java.util.List;

public interface IUserService {

    public List<User> usersActive();

    public User findById(Long id);

    public User findByDni(String dni);

    public List<Account> findByIdAccount(Long userId);

    public User createUser(UserDTO userDTO)throws Exception;

    public User deleteUser(User user) throws Exception;
    public User findByEmail(String email);

    public User editUser(Long id, User user) throws Exception;

}
