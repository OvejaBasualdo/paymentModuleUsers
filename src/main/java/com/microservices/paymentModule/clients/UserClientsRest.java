package com.microservices.paymentModule.clients;

import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.models.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-payments-account", url = "localhost:8002")
public interface UserClientsRest {

    @GetMapping("/api/accounts/list")
    public List<Account> getListAccounts();
    @GetMapping("/api/accounts/list/id/{id}")
    public Account getById(@PathVariable Long id);
    @GetMapping("/api/accounts/id/{idAccount}")
    public List<Account> getByUserId(@PathVariable Long idAccount);
    @GetMapping("/api/accounts/list/accountNumber/{accountNumber}")
    public Account getByAccountNumber(@PathVariable String accountNumber);

    @GetMapping("/api/accounts/list/cbu/{cbu}")
    public ResponseEntity<Object> getByCbu(@PathVariable String cbu);

    @PostMapping("/api/accounts/createAccount")
    public Account createAccount(@RequestBody User user);
    @PutMapping("/api/accounts/deleteAccount/{idAccount}")
    public ResponseEntity<Object> deleteAccount(@RequestBody User user, @PathVariable Long idAccount);
    @PutMapping("/api/accounts/deleteUserAccounts")
    public ResponseEntity<Object> deleteUserAccounts(@RequestBody User user) throws Exception;
}
