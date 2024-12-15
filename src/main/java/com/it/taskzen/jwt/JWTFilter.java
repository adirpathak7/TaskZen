package com.it.taskzen.jwt;

import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.services.UserService;
//import com.it.taskzen.services.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    @Lazy
    private JWTService jwtService;

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            email = jwtService.getUsernameFromToken(token);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity user = userService.findByEmail(email);

            if (jwtService.validateToken(token, user)) {
                String role = jwtService.getRoleFromToken(token); // Extract role if needed
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
