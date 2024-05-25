package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    @GetMapping()
    public List<UserDto> getAllUsers() {
        List<User> userList = userService.findAllUsers();
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }



    @GetMapping("/simple")
    public List<SimpleUserDto> getAllSimpleUsers() {
        List<User> userList = userService.findAllUsers();
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }


    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {

        System.out.println("User with e-mail: " + userDto.email() + " passed to the request");


        User newUser = userService.createUser(userMapper.toEntity(userDto));

        UserDto newUserDto = userMapper.toDto(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDto);
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


    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user with ID: " + userId);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<List<MailUserDto>> getUserByMail(@RequestParam String email){
            List<MailUserDto> mailUserDto = userService.findUsersByMail(email);
            return ResponseEntity.ok().body(mailUserDto);
        }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> getUsersOlderThanDate(@PathVariable LocalDate time) {
        List<User> users = userService.getUsersOlderThanDate(time);
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(userId, user);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
}

