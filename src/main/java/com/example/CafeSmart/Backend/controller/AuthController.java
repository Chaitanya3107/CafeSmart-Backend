package com.example.CafeSmart.Backend.controller;

import com.example.CafeSmart.Backend.model.User;
import com.example.CafeSmart.Backend.repository.UserRepository;
import com.example.CafeSmart.Backend.service.JwtService;
import com.example.CafeSmart.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    @Autowired
    UserService service;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;


    @PostMapping("register")
    public User register(@RequestBody User user){
        return service.saveUser(user);

    }

    @PostMapping("login")
    public String login(@RequestBody User user){
  // authentication with authenticationManager, it puts user data into authentication, after that authenticationProvider provides authentication object
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Login Failed";
    }



}
