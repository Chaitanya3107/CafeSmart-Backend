package com.example.CafeSmart.Backend.service;

import com.example.CafeSmart.Backend.model.User;
import com.example.CafeSmart.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    UserRepository repo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
            return repo.save(user);
    }
}
