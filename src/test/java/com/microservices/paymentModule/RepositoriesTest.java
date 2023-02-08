package com.microservices.paymentModule;

import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    UserRepository userRepository;


/*
    @Test
    public void unitaryUser(){
        User user = userRepository.findById(1L).orElse(null);
        assertThat(user,notNullValue());
    }*/

}
