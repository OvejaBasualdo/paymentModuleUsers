package com.accenture.paymentModule.clients;

import com.accenture.paymentModule.entity.User;
import com.accenture.paymentModule.models.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "microservice-payments-account", url = "localhost:8002")
public interface UserClientsRest {

    @GetMapping("/api/accounts/list")
    public List<Account> getListAccounts();
    @GetMapping("/api/accounts/list/id/{id}")
    public Account getById(@PathVariable Long id);
    @GetMapping("/api/accounts/list/userId/{userId}")
    public List<Account> getByUserId(@PathVariable Long userId);
    @GetMapping("/api/accounts/list/accountNumber/{accountNumber}")
    public Account getByAccountNumber(@PathVariable String accountNumber);

    @GetMapping("/api/accounts/list/cbu/{cbu}")
    public ResponseEntity<Object> getByCbu(@PathVariable String cbu);

    @PostMapping("/api/accounts/createAccount")
    public Account createAccount(@RequestBody User user);
}
