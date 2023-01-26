package com.accenture.paymentModule.utils;

import com.accenture.paymentModule.dto.UserDTO;
import com.accenture.paymentModule.entity.ResultsType;
import com.accenture.paymentModule.entity.User;

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

    public static ResultsType checkingData(UserDTO userDTO, User user) {
        int a = userDTO.getDni().length();
        if (!(a > 6 && a < 9)) {
            return ResultsType.DNI_NUMBER_ERROR;
        }
        if (!UserUtils.verifyNumber(userDTO.getDni())) {
            return ResultsType.DNI_ERROR;
        }
        if (userDTO.getFirstName().isEmpty() || userDTO.getLastName().isEmpty() || userDTO.getDni().isEmpty()
                || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty()) {
            return ResultsType.EMPTY_DATA;
        }
        if (user != null) {
            if (user.getDni().equals(userDTO.getDni())) {
                if (!user.getFirstName().equals(userDTO.getFirstName())) {
                    return ResultsType.DNI_EXISTENT;
                }
                if (!user.getLastName().equals(userDTO.getLastName())) {
                    return ResultsType.DNI_EXISTENT;
                }
                if (!user.getEmail().equals(userDTO.getEmail())) {
                    return ResultsType.DNI_EXISTENT;
                }
            }
        }
        return null;
    }
}
