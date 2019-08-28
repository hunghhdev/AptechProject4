package com.aptech.project.hotel.security;

import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.UserSecurity;
import com.aptech.project.hotel.service.JwtService;
import com.aptech.project.hotel.service.RoleService;
import com.aptech.project.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader("Authorization");

            if (jwt!=null&&jwtService.validateTokenLogin(jwt)) {
                User user = userService.findByUsername(jwtService.getUsernameFromToken(jwt));
                if(user.getJwtKey()!=null && user.getJwtKey().equals(jwt)){
                UserDetails userDetails = new UserSecurity(user.getId(),
                        user.getUsername(),user.getPassword(),true,true,
                        true,true,roleService.getAuthorities(user.getRoleId()));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

}
