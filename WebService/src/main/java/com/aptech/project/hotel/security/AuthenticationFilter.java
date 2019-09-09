package com.aptech.project.hotel.security;

import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.UserSecurity;
import com.aptech.project.hotel.service.JwtService;
import com.aptech.project.hotel.service.RoleService;
import com.aptech.project.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader("Authorization");

            if (jwt!=null&&jwtService.validateTokenLogin(jwt)) {
                User user = userService.findByUsername(jwtService.getUsernameFromToken(jwt));
                if(user != null && user.getJwtKey()!=null && user.getJwtKey().equals(jwt)){
                    UserSecurity userDetails = new UserSecurity(
                            user.getUsername(),user.getPassword(),true,true,
                        true,true,roleService.getAuthorities(user.getRoleId()));
                    userDetails.setId(user.getId());
                    userDetails.setBranchPlaceId(user.getBranchPlaceId());
                    userDetails.setPersonnelLevel(user.getPersonnelLevel());
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
