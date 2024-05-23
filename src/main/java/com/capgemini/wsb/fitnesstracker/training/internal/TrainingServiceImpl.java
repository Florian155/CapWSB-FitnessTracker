package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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


    public List<Training> getAllTrainingsForDedicatedEndTime(Date endTime) {
        return trainingRepository.findAll().stream()
                .filter(training -> {
                    // Porównanie dat bez uwzględniania czasu
                    return training.getEndTime().getYear() == endTime.getYear() &&
                            training.getEndTime().getMonth() == endTime.getMonth() &&
                            training.getEndTime().getDate() == endTime.getDate();
                })
                .collect(Collectors.toList());
    }
    public List<Training> getAllTrainingsForActivity(ActivityType activityType) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getActivityType().equals(activityType))
                .collect(Collectors.toList());
    }

    @Override
    public Training createTraining(Training training) {
        // Tutaj możesz dodać logikę walidacji lub innych operacji przed zapisaniem treningu
        return trainingRepository.save(training);
    }
}




