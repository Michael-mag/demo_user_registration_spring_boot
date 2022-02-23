package com.michael.magaisa.demo_user_registration_app.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @Override
    public boolean test(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return patternMatches(emailAddress, regexPattern);
    }
}
