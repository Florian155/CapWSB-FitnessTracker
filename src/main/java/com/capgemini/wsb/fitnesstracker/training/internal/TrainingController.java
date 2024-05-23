package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor

public class TrainingController {
    private final UserServiceImpl userService;
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings().stream().map(trainingMapper::toDto).toList();
    }
    @GetMapping("/{userId}")
    public   List<TrainingDto> getTrainingByUserId(@PathVariable Long userId){
        return trainingService.getAllTrainingsForDedicatedUser(userId).stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("time/{endTime}")
    public List<TrainingDto> getTrainingByEndTime(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endTime) {
        return trainingService.getAllTrainingsForDedicatedEndTime(endTime).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/param")
    public List<TrainingDto> getAllTrainingsForActivity(@RequestParam ActivityType activityType) {
        List<Training> trainings = trainingService.getAllTrainingsForActivity(activityType);
        return trainings.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }
    @PostMapping("/addTraining")
    public ResponseEntity<?> addTraining(@RequestBody TrainingDto trainingDto, @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body("User ID is required");
        } else {
            Optional<User> optionalUser = userService.getUser(userId);
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                Training newTraining = new Training();
                newTraining.setUser(existingUser);

                Training createdTraining = trainingService.createTraining(newTraining);
                return ResponseEntity.ok(createdTraining);
            } else {
                return ResponseEntity.badRequest().body("User with ID " + userId + " not found");
            }
        }
    }
    }





