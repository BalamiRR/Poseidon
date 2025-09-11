package com.nnk.springboot.config;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password){
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
