package com.accenture.paymentModule.service;

import com.accenture.paymentModule.clients.UserClientsRest;
import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.entity.ResultsType;
import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.models.Account;
import com.accenture.paymentModule.repository.UserRepository;
import com.accenture.paymentModule.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userServiceFeign")
public class UserServiceFeign implements IUserService {
    @Autowired
    UserClientsRest userClientsRestFeign;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByFirstNameIgnoreCase(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLastNameIgnoreCase(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByDni(String dni) {
        return userRepository.findByDni(dni).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Long findByAccountId(Long accountId) {
        return userRepository.findByAccountId(accountId).orElse(null);
    }

    @Override
    public List<Account> findAccountFromUserId(Long userId) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("userId", userId.toString());
        List<Account> accountsById = userClientsRestFeign.getByUserId(userId);
        return accountsById;
    }


    public ResultsType createUser(UserDTO userDTO) {
        User user = userRepository.findByDni(userDTO.getDni()).orElse(null);
        if (UserUtils.checkingData(userDTO, user) != null) {
            return UserUtils.checkingData(userDTO, user);
        }
        if (user != null) {
            setAccountToUser(user);
            userRepository.save(user);
            return ResultsType.ACCOUNT_ADDED;
        } else {
            user = new User(userDTO);
            userRepository.save(user);
            setAccountToUser(user);
            userRepository.save(user);
            return ResultsType.SUCCESS;
        }
    }

    public User setAccountToUser(User user) {
        userClientsRestFeign.createAccount(user);
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("userId", user.getId().toString());
        List<Account> accounts = userClientsRestFeign.getByUserId(user.getId());
        Set<Long> accountsId = new HashSet<>();
        for (Account aux : accounts) {
            accountsId.add(aux.getId());
        }
        user.setAccountId(accountsId);
        return user;

    }
}
