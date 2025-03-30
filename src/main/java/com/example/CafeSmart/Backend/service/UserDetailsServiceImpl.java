package com.example.CafeSmart.Backend.service;
import com.example.CafeSmart.Backend.model.User;
import com.example.CafeSmart.Backend.model.UserDetailImpl;
import com.example.CafeSmart.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override  // use this class to interact with database, fire query with the help of repo class to check for user in database, if user found return UserDetails obj.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = repo.findByUsername(username);
        Optional<User> user = Optional.ofNullable(repo.findByUsername(username));
        if(user.isEmpty()){
            System.out.println("User not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UserDetailImpl(user.orElse(null)); // user principal is the current user details
    }
}
