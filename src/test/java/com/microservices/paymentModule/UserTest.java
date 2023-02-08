package com.microservices.paymentModule;

import com.microservices.paymentModule.dto.UserDTO;
import com.microservices.paymentModule.entity.User;
import com.microservices.paymentModule.exceptions.DniException;
import com.microservices.paymentModule.service.UserServiceFeign;
import com.microservices.paymentModule.utils.UserUtils;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.Matchers.containsString;

@DisplayName("USER TEST")
public class UserTest {
    private UserServiceFeign userServiceFeign;

    @ParameterizedTest
    @DisplayName("TESTING DATA FILE")
    @CsvFileSource(resources = "/dataForUser.csv")
    void dataTest(String firstName, String lastName,String dni,String email, String password){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDni(dni);
        user.setEmail(email);
        user.setPassword(password);
        Assertions.assertAll(
                ()->{
                    Assertions.assertNotNull(user.getFirstName(),()->"Name not be null");
                    Assertions.assertNotNull(user.getLastName(),()->"Lastname not be null");
                    Assertions.assertNotNull(user.getDni(),()->"dni not be null");
                    Assertions.assertNotNull(user.getEmail(),()->"Mail not be null");
                    Assertions.assertNotNull(user.getPassword(),()->"Password not be null");
                },
                ()->{
                    String  wait = "Pablo";
                    String real = user.getFirstName();
                    String  wait1 = "Sanchez";
                    String real2 = user.getLastName();
                    Assertions.assertEquals(wait,real,()->"Name must be same");
                    Assertions.assertEquals(wait1,real2, ()->"Lastname must be same");
                },
                ()->{
                    String real2 = user.getLastName();
                    Assertions.assertTrue(real2.equals("Sanchez"),()->"Lastname must be same");
                    MatcherAssert.assertThat(user.getEmail(),containsString("@"));
                }
        );
    }

}
