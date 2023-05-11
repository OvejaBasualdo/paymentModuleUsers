package com.microservices.paymentModule;

import com.microservices.paymentModule.entity.Role;
import com.microservices.paymentModule.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@EnableDiscoveryClient//en vez de @EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class PaymentModuleApplication {


    public static void main(String[] args) {
        SpringApplication.run(PaymentModuleApplication.class, args);
    }

}
