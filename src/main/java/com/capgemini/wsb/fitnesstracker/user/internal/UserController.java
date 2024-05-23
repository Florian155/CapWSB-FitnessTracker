package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        User newUser = userService.createUser(userMapper.toEntity(userDto));

        // Mapowanie użytkownika na DTO i zwracanie odpowiedzi z kodem 201 CREATED
        UserDto newUserDto = userMapper.toDto(newUser);
        return newUser;
    }

    @GetMapping("/{userId}")
    public Optional<User> getUserDetails(@PathVariable Long userId) {
        return userService.getUserDetails(userId);

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


    @GetMapping("/{email}")
    public   List<MailUserDto> getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email).stream().map(UserMapper::toMailUserDto).collect(Collectors.toList());
    }
    }
