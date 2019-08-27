package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.model.UserDto;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
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
        userDto.setRoleId(user.getRoleId());
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
            userDto.setRoleId(user.getRoleId());
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
        user.setRoleId(userDto.getRoleId());
        user.setCreatedBy(userDto.getCreatedBy());
        user.setCreatedDate(userDto.getCreatedDate());
        user.setAvatar(userDto.getAvatar());
        return user;
    }

    public UserInfoDto toUserInfoDto(User user){
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setAvatar(user.getAvatar());
        return userInfoDto;
    }
}
