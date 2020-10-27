package com.drbsimon.apigateway.security;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class DataValidatorService {

    private final Pattern specialCharacters = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    private final Pattern upperCaseLetters = Pattern.compile("[A-Z ]");
    private final Pattern lowerCaseLetters = Pattern.compile("[a-z ]");
    private final Pattern digitsPattern = Pattern.compile("[0-9 ]");

    private final String emailPattern = "^(.+)@(.+)$";

    public boolean isValidUsername(String username, List<String> errorList) {
        errorList.clear();
        boolean valid = true;
        if (username.length() < 6) {
            errorList.add("at least 6 characters");
            valid = false;
        }
        if (username.contains(" ")) {
            errorList.add("no whitespaces");
            valid = false;
        }
        return valid;
    }

    public boolean isValidPassword(String password, List<String> errorList) {
        errorList.clear();
        boolean valid = true;
        if (password.length() < 8) {
            errorList.add("at least 8 characters");
            valid = false;
        }
        if (!upperCaseLetters.matcher(password).find()) {
            errorList.add("at least 1 uppercase letter");
            valid = false;
        }
        if (!lowerCaseLetters.matcher(password).find()) {
            errorList.add("at least 1 lowercase letter");
            valid = false;
        }
        if (!digitsPattern.matcher(password).find()) {
            errorList.add("at least 1 digit");
            valid = false;
        }
        if (password.contains(" ")) {
            errorList.add("no whitespaces");
            valid = false;
        }
        return valid;
    }

    public boolean isValidEmail(String email, List<String> errorList) {
        errorList.clear();
        return email.matches(emailPattern);
    }

    public boolean isValidName(String name, List<String> errorList) {
        errorList.clear();
        boolean valid = true;
        if (name.length() < 2) {
            errorList.add("should be at least 2 characters long");
            valid = false;
        }
        if (name.length() == 0) {
            errorList.add("cannot leave empty");
            valid = false;
        }
        if (name.contains(" ")) {
            errorList.add("cannot contain whitespaces");
            valid = false;
        }
        if (specialCharacters.matcher(name).find()) {
            errorList.add("cannot contain special character");
            valid = false;
        }
        if (digitsPattern.matcher(name).find()) {
            errorList.add("cannot contain digit");
            valid = false;
        }
        return valid;
    }
}
