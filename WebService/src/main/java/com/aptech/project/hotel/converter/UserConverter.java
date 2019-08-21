package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.dto.UserDto;
import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setPermission(user.getRole().getPermissions());
        return userDto;
    }

    public User toUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        Role role = new Role();
        role.setId(userDto.getRole());
        user.setRole(role);
        return user;
    }
}
