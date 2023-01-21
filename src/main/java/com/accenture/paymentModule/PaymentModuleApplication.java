package com.accenture.paymentModule;

import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentModuleApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return (args) -> {

            User user = new User("Juan", "Perez", "20.876.444", "juan.perez@gmail.com", "1234");
            userRepository.save(user);
            User user1 = new User("Camila", "Rodriguez", "30.543.123", "cami.ro@gmail.com", "5678");
            userRepository.save(user1);
            User user2 = new User("Marcelo", "Alvarez", "30.451.324", "marcelo@gmail.com", "1357");
            userRepository.save(user2);
        };
    }
}
