package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.model.UserDto;
import com.aptech.project.hotel.entity.Role;
import com.aptech.project.hotel.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setPermission(user.getRole().getPermissions());
        userDto.setRole(user.getRole().getId());
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setAvatar(user.getAvatar());
        return userDto;
    }

    public List<UserDto> toUsersDto(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setPermission(user.getRole().getPermissions());
            userDto.setRole(user.getId());
            userDto.setCreatedBy(user.getCreatedBy());
            userDto.setCreatedDate(user.getCreatedDate());
            userDto.setAvatar(user.getAvatar());

            userDtos.add(userDto);
        }
        return userDtos;
    }

    public User toUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        Role role = new Role();
        role.setId(userDto.getRole());
        user.setRole(role);
        user.setCreatedBy(userDto.getCreatedBy());
        user.setCreatedDate(userDto.getCreatedDate());
        user.setAvatar(userDto.getAvatar());
        return user;
    }
}
