package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor

 class TrainingController {
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

    @GetMapping("/finished/{endTime}")
    public List<TrainingDto> getTrainingByEndTime(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endTime) {
        return trainingService.getAllTrainingsForDedicatedEndTime(endTime).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingsForActivity(@RequestParam ActivityType activityType) {
        List<Training> trainings = trainingService.getAllTrainingsForActivity(activityType);
        return trainings.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<TrainingDto> addTraining(@RequestBody Training training, Long userId) {


        Training createdTraining = trainingService.createTraining(training, userId);
        TrainingDto createdTrainingDto = trainingMapper.toDto(createdTraining);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrainingDto);
    }


    @PutMapping("{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training updatedTraining = trainingService.update(trainingId, training);
        return ResponseEntity.ok(trainingMapper.toDto(updatedTraining));
    }
}







