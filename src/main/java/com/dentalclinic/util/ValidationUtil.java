package com.dentalclinic.util;

public class ValidationUtil {


    // Check empty values
    public static boolean isEmpty(String value) {


        return value == null || value.trim().isEmpty();

    }

    // Validate phone number
    public static boolean isValidPhone(String phone) {


        if (phone == null) {
            return false;
        }

        return phone.matches("[0-9]{10}");

    }

    // Validate email
    public static boolean isValidEmail(String email) {


        if (email == null) {
            return false;
        }


        return email.matches(
                "^[A-Za-z0-9+_.-]+@(.+)$"
        );

    }



    // Validate positive numbers
    public static boolean isPositiveNumber(int number) {


        return number > 0;

    }



    // Validate password length
    public static boolean isValidPassword(String password) {


        return password != null &&
                password.length() >= 6;

    }

}