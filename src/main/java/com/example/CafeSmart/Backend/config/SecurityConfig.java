package com.example.CafeSmart.Backend.config;

import com.example.CafeSmart.Backend.repository.UserRepository;
import com.example.CafeSmart.Backend.service.JwtService;
import com.example.CafeSmart.Backend.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;
    private final BCryptPasswordEncoder encoder; // Store encoder as a field


    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtFilter jwtFilter,
                          BCryptPasswordEncoder encoder,
                          JwtService jwtService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
        this.encoder = encoder;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder); // Use injected encoder
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll() // Allow POST login/register
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    @Bean
    public static BCryptPasswordEncoder encoder() { // Mark as static to avoid circular dependency
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

