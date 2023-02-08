package com.microservices.paymentModule.utils;

import com.microservices.paymentModule.dto.UserDTO;
import com.microservices.paymentModule.entity.User;

public final class UserUtils {
    public static Boolean verifyNumber(String number) {
        try {
            Long a = Long.parseLong(number);
            return true;
        } catch (NumberFormatException e) {
            e.getMessage();
            return false;
        }
    }

    public static Boolean checkingData(UserDTO userDTO, User user) {
        if (user != null) {
            if (user.getDni().equals(userDTO.getDni())) {
                if (!user.getFirstName().equals(userDTO.getFirstName())) {
                    return true;
                }
                if (!user.getLastName().equals(userDTO.getLastName())) {
                    return true;
                }
                if (!user.getEmail().equals(userDTO.getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean dniExtensionIsCorrect(String dni) {
        if (!(dni.length() > 6 && dni.length() < 9)) {
            return Boolean.TRUE;
        } else {
            return false;
        }
    }

    public static Boolean hasEmptyData(User user) {
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getDni().isEmpty()
                || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    public static Boolean hasDTOEmptyData(UserDTO user) {
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() || user.getDni().isEmpty()
                || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
