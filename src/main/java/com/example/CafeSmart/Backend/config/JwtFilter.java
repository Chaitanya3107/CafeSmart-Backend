package com.example.CafeSmart.Backend.config;

import com.example.CafeSmart.Backend.model.UserDetailImpl;
import com.example.CafeSmart.Backend.service.JwtService;
import com.example.CafeSmart.Backend.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // checking for if authHeader is present or not
        if(authHeader!=null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);

//        SecurityContextHolder.getContext().getAuthentication() this holds authentication object, so if its null, then there is no authentication object
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);// loading username form db
                if (jwtService.validateToken(token, userDetails)) {
//              generates authentication object for UsernamePasswordAuthenticationFilter
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // In your JwtFilter's doFilterInternal method:
        filterChain.doFilter(request,response);

    }
}
