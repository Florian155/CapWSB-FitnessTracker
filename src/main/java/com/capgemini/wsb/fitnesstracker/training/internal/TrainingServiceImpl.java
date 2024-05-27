package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



// TODO: Provide Impl
@Service
@RequiredArgsConstructor
@Slf4j
class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    private final UserService userService;


    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getAllTrainingsForDedicatedUser(Long userId) {
        return trainingRepository.findAll().stream().filter(training -> training.getUser().getId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public List<Training> getAllTrainingsForDedicatedEndTime(Date endTime) {
        LocalDateTime endOfDay = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(LocalTime.MAX);
        return trainingRepository.findAll().stream()
                .filter(training -> {
                    LocalDateTime trainingEndTime = training.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return !trainingEndTime.isBefore(endOfDay);
                })
                .collect(Collectors.toList());
    }

    public List<Training> getAllTrainingsForActivity(ActivityType activityType) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getActivityType() == activityType)
                .collect(Collectors.toList());
    }
    @Override
    public Training createTraining(TrainingRequest trainingRequest) {
        if (trainingRequest.getUserId() == null) {
            throw new IllegalArgumentException("Użytkownik nie może być null");
        }

        Optional<User> optionalUser = userService.getUser(trainingRequest.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Nie znaleziono użytkownika o id: " + trainingRequest.getUserId());
        }
        User user = optionalUser.get();

        Training training = new Training();
        training.setUser(user);
        training.setAverageSpeed(trainingRequest.getAverageSpeed());
        training.setDistance(trainingRequest.getDistance());
        training.setStartTime(trainingRequest.getStartTime());
        training.setEndTime(trainingRequest.getEndTime());
        training.setActivityType(trainingRequest.getActivityType());

        return trainingRepository.save(training);
    }







    public Training update(Long trainingId, Training training) {
        Training existingTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException("Training not found"));

        if (training.getDistance() != 0) {
            existingTraining.setDistance(training.getDistance());
        }
        if (training.getUser() != null && training.getUser().getId() != null) {
            Optional<User> optionalUser = userService.getUser(training.getUser().getId());
            if (optionalUser.isPresent()) {
                existingTraining.setUser(optionalUser.get());
                if (training.getActivityType() != null) {
                    existingTraining.setActivityType(training.getActivityType());
                }
                if (training.getStartTime() != null) {
                    existingTraining.setStartTime(training.getStartTime());
                }
                if (training.getEndTime() != null) {
                    existingTraining.setEndTime(training.getEndTime());
                }
                if (training.getAverageSpeed() != 0) {
                    existingTraining.setAverageSpeed(training.getAverageSpeed());
                }
                if (!existingTraining.equals(training)) {
                    existingTraining = trainingRepository.save(existingTraining);
                }


            }

    }return existingTraining;
    }
}



