package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<SimpleUserDto> getAllUsers() {
        List<User> userList = userService.findAllUsers();
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }


    @PostMapping("/addUser")
    public User addUser(@RequestBody UserDto userDto) {

        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        User newUser = userService.createUser(userMapper.toEntity(userDto));

        UserDto newUserDto = userMapper.toDto(newUser);
        return newUser;
    }

    @GetMapping("/{param}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable String param) {
        Optional<User> optionalUser = userService.getUserByParam(param);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = userMapper.toDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with parameter: " + param);
        }
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with ID: " + userId + " has been successfully deleted.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user with ID: " + userId);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<MailUserDto>> getUserByMail(@PathVariable String email) {
        List<MailUserDto> mailUserDto = userService.findUserByMail(email);
        return ResponseEntity.ok().body(mailUserDto);
    }

    @GetMapping("/details")
    public ResponseEntity<List<UserDto>> getUsersOlderThanAge(@RequestParam int age) {
        List<User> users = userService.getUsersOlderThanAge(age);
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(userId, user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
}

