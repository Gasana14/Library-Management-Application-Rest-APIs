package com.Library.Management.Rest.APIs.services;

import com.Library.Management.Rest.APIs.dtos.LoginDto;
import com.Library.Management.Rest.APIs.dtos.RegisterDto;
import com.Library.Management.Rest.APIs.exception.LibraryManagementException;
import com.Library.Management.Rest.APIs.jwt.JwtTokenProvider;
import com.Library.Management.Rest.APIs.models.Role;
import com.Library.Management.Rest.APIs.models.User;
import com.Library.Management.Rest.APIs.repositories.RoleRepository;
import com.Library.Management.Rest.APIs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    //  Login with Basic Auth
//    public String login(LoginDto loginDto){
//    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));
//
//    // place authentication into security context holder
//      SecurityContextHolder.getContext().setAuthentication(authentication);
//
//      return "User Logged in Successfully";
//    }

    //  Login with JWT Token

    public String login(LoginDto loginDto) {
        Authentication authentication =   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        // store our authentication into Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateJwtToken(authentication);

        return token;
    }

    public String register(RegisterDto registerDto){

        // check if user with username exist
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new LibraryManagementException(HttpStatus.BAD_REQUEST,"username "+registerDto.getUsername()+" is already exist.");
        }

        // check if user with email exist
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new LibraryManagementException(HttpStatus.BAD_REQUEST,"email "+registerDto.getEmail()+" is already exist.");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // assign roles to user
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();

        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User is registered successfully";
    }



}
