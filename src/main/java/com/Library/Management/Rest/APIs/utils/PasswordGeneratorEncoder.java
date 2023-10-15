package com.Library.Management.Rest.APIs.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Admin : "+passwordEncoder.encode("admin"));
        System.out.println("Cody : "+passwordEncoder.encode("123"));
    }
}
