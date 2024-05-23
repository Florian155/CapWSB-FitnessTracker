package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }
    SimpleUserDto toSimpleDto(User user) {
        return new SimpleUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
                );
    }
    static MailUserDto toMailUserDto(User user) {
        return new MailUserDto(
                user.getId(),
                user.getEmail()
        );
    }




}
