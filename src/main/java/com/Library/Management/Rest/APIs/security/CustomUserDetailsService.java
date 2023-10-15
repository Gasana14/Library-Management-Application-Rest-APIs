package com.Library.Management.Rest.APIs.security;


import com.Library.Management.Rest.APIs.models.User;
import com.Library.Management.Rest.APIs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // user from entity (User)
       User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username or email :"+usernameOrEmail));

       // user has set of roles (Role Entity), and we need to convert roles into GrantAuthorities so that spring boot security
        // understand the roles as authorities
      Set<GrantedAuthority> authorities = user.getRoles()
              .stream()
              .map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());


      // we have to convert the user Entity (User) to UserDetails (Spring security User)
      return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }
}
