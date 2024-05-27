package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<Training> getAllTrainingsForDedicatedUser(Long userId);
    List<Training> getAllTrainingsForDedicatedEndTime(Date endTime);
     Training createTraining(TrainingRequest trainingRequest);
    Training update(Long trainingId, Training training);
    List<Training> getAllTrainingsForActivity(ActivityType activityType);
    List<Training> getAllTrainings();


    }


