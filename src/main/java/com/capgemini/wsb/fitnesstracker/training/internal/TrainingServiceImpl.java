package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserService userService;
    private final UserMapper userMapper;

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
    public Training createTraining(Training training, Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = userService.getUser(userId);
            if (!optionalUser.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID is not valid");
            }
            training.setUser(optionalUser.get());
        }
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
                // Użytkownik o podanym ID istnieje, można kontynuować aktualizację treningu
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
                // Zapisujemy tylko, jeśli dokonano zmian w istniejącym użytkowniku
                if (!existingTraining.equals(training)) {
                    existingTraining = trainingRepository.save(existingTraining);
                }


            }

    }return existingTraining;
    }
}



