package com.microservices.paymentModule.service;

import com.microservices.paymentModule.clients.UserClientsRest;
import com.microservices.paymentModule.dto.UserDTO;
import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.exceptions.DniException;
import com.microservices.paymentModule.exceptions.ElementNotFoundException;
import com.microservices.paymentModule.exceptions.EmptyDataException;
import com.microservices.paymentModule.models.Account;
import com.microservices.paymentModule.repository.UserRepository;
import com.microservices.paymentModule.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("userServiceFeign")
@Primary
public class UserServiceFeign implements IUserService {
    @Autowired
    UserClientsRest userClientsRestFeign;

    @Autowired
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserServiceFeign.class);

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findByIdAndIsActiveTrue(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByDni(String dni) {
        return userRepository.findByDni(dni).orElse(null);
    }


    @Override
    public List<Account> findByIdAccount(Long idAccount) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("idAccount", idAccount.toString());
        List<Account> accountsById = userClientsRestFeign.getByUserId(idAccount);
        return accountsById;
    }


    public User createUser(UserDTO userDTO) throws Exception {
        logger.info("Starting with process: CREATE A USER");
        User user = userRepository.findByDni(userDTO.getDni()).orElse(null);
        logger.info("Checking dni");
        if (UserUtils.dniExtensionIsCorrect(userDTO.getDni().trim())) {
            logger.warn("ERROR: WRONG Dni extension");
            throw new DniException("WRONG Dni extension");
        }
        logger.info("Checking dni digits");
        if (!UserUtils.verifyNumber(userDTO.getDni().trim())) {
            throw new DniException("Please insert only digits");
        }
        logger.info("Checking empty data");
        if (UserUtils.hasDTOEmptyData(userDTO)) {
            throw new EmptyDataException("At least one field is empty");
        }
        if (UserUtils.checkingData(userDTO,user)){
            throw new Exception("This user already exist");
        }
        if (user != null) {
            logger.info("User registered, adding account");
            Account accountCreated = userClientsRestFeign.createAccount(user);
            if (accountCreated == null) {
                logger.warn("ACCOUNT NOT CREATED");
                throw new Exception("ACCOUNT NOT CREATED");
            }
            logger.info("Account created");
            return user;
        } else {
            logger.info("Creating user");
            user = new User(userDTO);
            userRepository.save(user);
            user = userRepository.findByDni(userDTO.getDni()).orElse(null);
            logger.info("User registered, adding account");
            Account accountCreated = userClientsRestFeign.createAccount(user);
            if (accountCreated == null) {
                logger.warn("ACCOUNT NOT CREATED");
                throw new Exception("ACCOUNT NOT CREATED");
            }
            logger.info("Account created");
            return user;
        }
    }

    @Override
    public User deleteUser(User user) throws Exception {
        logger.info("Starting with process: DELETE AN USER");
        User userToFind = userRepository.findByIdAndIsActiveTrue(user.getId()).orElse(null);
        if (userToFind == null) {
            logger.warn("USER NOT FOUND");
            throw new ElementNotFoundException("User not found");
        }
        try {
            logger.info("Searching account/s to delete");
            userClientsRestFeign.deleteUserAccounts(userToFind);
            logger.info("Account/s deleted");
            userToFind.setIsActive(false);
            userToFind = userRepository.save(userToFind);
            return userToFind;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public User editUser(Long id, User user) throws Exception {
        User userToEdit = userRepository.findById(id).orElse(null);
        if (userToEdit == null) {
            throw new Exception("The user doesn't exist");
        }
        if (UserUtils.dniExtensionIsCorrect(user.getDni().trim())) {
            throw new DniException("WRONG Dni extension");
        }
        if (!UserUtils.verifyNumber(user.getDni().trim())) {
            throw new DniException("Please insert only digits");
        }
        if (UserUtils.hasEmptyData(user)) {
            throw new EmptyDataException("At least one field is empty");
        }
        user.setIsActive(userToEdit.getIsActive());
        User userFinal = userRepository.save(user);
        return userFinal;
    }

    @Override
    public List<User> usersActive() {
        List<User> active = userRepository.findByIsActiveTrue();
        return active;
    }

}
