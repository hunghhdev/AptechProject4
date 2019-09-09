package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserSecurity extends User {
    private int id;
    private int personnelLevel;
    private int branchPlaceId;

    public UserSecurity(String username, String password,
                        boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                        boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked, authorities);
    }
}
