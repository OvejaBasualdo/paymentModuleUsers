package com.accenture.paymentModule.entity;

import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.models.Account;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private String password;
    @Transient
    private Set<Account> accounts=new HashSet<>();
    private Set<Long> accountId=new HashSet<>();

    public Set<Long> getAccountId() {
        return accountId;
    }

    public void setAccountId(Set<Long> accountId) {
        this.accountId = accountId;
    }

    public User() {
    }

    public User(String firstName, String lastName, String dni, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.email = email;
        this.password = password;
    }

    public User(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.dni = userDTO.getDni();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
