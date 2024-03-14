package com.calculator.cal.mapper;

import com.calculator.cal.dto.UserDto;
import com.calculator.cal.dto.UserForm;
import com.calculator.cal.model.User;

public class UserMapper {

    public static User mapToUser(UserForm userForm) {
        return User.builder()
                .email(userForm.getEmail())
                .username(userForm.getUsername())
                .password(userForm.getPassword())
                .build();
    }

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .build();
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
