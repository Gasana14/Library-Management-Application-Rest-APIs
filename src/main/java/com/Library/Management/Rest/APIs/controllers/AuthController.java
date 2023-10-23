package com.Library.Management.Rest.APIs.controllers;

import com.Library.Management.Rest.APIs.dtos.LoginDto;
import com.Library.Management.Rest.APIs.dtos.RegisterDto;
import com.Library.Management.Rest.APIs.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;




    // Build Login REST API
    @PostMapping(value = {"/login","signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
       String response =  authService.login(loginDto);
       return ResponseEntity.ok(response);
    }

    // Build Register REST API
    @PostMapping(value = {"/register","signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


}
