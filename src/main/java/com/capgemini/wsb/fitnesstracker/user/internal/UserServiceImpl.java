package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    public List<User> getUsersOlderThanAge(int age) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDateThreshold = currentDate.minusYears(age);

        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> calculateAge(user.getBirthdate()) > age)
                .collect(Collectors.toList());
    }
    public User update(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getBirthdate() != null) {
            existingUser.setBirthdate(user.getBirthdate());
        }

        // Zapisujemy tylko, jeśli dokonano zmian w istniejącym użytkowniku
        if (!existingUser.equals(user)) {
            existingUser = userRepository.save(existingUser);
        }

        return existingUser;
    }




    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException();
        }


    }

    public List<MailUserDto> findUserByMail(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .map(UserMapper::toMailUserDto)
                .collect(Collectors.toList());
    }

    public Optional<User> getUserByParam(String param) {
        // Convert param to lower case for case-insensitive comparison
        String paramLowerCase = param.toLowerCase();

        // Filter users based on the provided parameter
        return userRepository.findAll().stream()
                .filter(user ->
                        // Check if any of the user's fields match the parameter (case-insensitive)
                        user.getFirstName().toLowerCase().equals(paramLowerCase) ||
                                user.getLastName().toLowerCase().equals(paramLowerCase) ||
                                user.getEmail().toLowerCase().equals(paramLowerCase) ||
                                String.valueOf(user.getId()).equals(param) || // Assuming ID is a String
                                user.getBirthdate().toString().equals(param) // Assuming birthdate is a String
                )
                .findFirst();
}}




