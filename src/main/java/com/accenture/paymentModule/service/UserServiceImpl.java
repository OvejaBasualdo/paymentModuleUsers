package com.accenture.paymentModule.service;

import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.models.Account;
import com.accenture.paymentModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    public User findByFirstNameIgnoreCase(String firstName){
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
}
