package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor

public class TrainingServiceImpl implements TrainingProvider, TrainingService{

    private final  TrainingRepository trainingRepository;
    private final  TrainingMapper trainingMapper;
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
        return trainingRepository.findAll().stream().filter(training -> training.getEndTime().equals(endTime)).collect(Collectors.toList());
    }}
//    @Override
//    public Training createTraining(User user, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
//        User user = userService.
//
//        // Create a new Training object
//        Training training = new Training(user, endTime, activityType, distance, averageSpeed);
//
//
//        // Save the Training object
//        return trainingRepository.save(training);
//    }
//}




