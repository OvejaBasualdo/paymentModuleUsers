package com.accenture.paymentModule.service;

import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.models.Account;
import com.accenture.paymentModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate accountRest;

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

    public List<Long> findAccountFromUserId(Long userId) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("userId", userId.toString());
        List<Long> accountsId = Arrays.stream(accountRest.getForObject("http://localhost:8002/api/accounts/list/userId/{userId}", Long[].class, pathVariables)).toList();
        return accountsId;
    }

    public void createUser(UserDTO userDTO) {
        User userWithoutAccount = new User(userDTO);
        userRepository.save(userWithoutAccount);
        User user = userRepository.findByDni(userWithoutAccount.getDni()).orElse(null);
        accountRest.postForEntity("http://localhost:8002/api/accounts/createAccount", user, Account.class);
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("userId", user.getId().toString());
        List<Account> accounts = Arrays.stream(accountRest.getForObject("http://localhost:8002/api/accounts/list/userId/{userId}", Account[].class, pathVariables)).toList();
        Set<Long> accountsId = new HashSet<>();
        for (Account aux : accounts) {
            accountsId.add(aux.getId());
        }
        user.setAccountId(accountsId);
        userRepository.save(user);
    }
    public void createAccountToUser(UserDTO userDTO) {
        User user = userRepository.findByDni(userDTO.getDni()).orElse(null);
        accountRest.postForEntity("http://localhost:8002/api/accounts/createAccount", user, Account.class);
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("userId", user.getId().toString());
        List<Account> accounts = Arrays.stream(accountRest.getForObject("http://localhost:8002/api/accounts/list/userId/{userId}", Account[].class, pathVariables)).toList();
        Set<Long> accountsId = new HashSet<>();
        for (Account aux : accounts) {
            accountsId.add(aux.getId());
        }
        user.setAccountId(accountsId);
        userRepository.save(user);
    }
}
