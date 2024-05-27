package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<Training> getAllTrainingsForDedicatedUser(Long userId);
    List<Training> getAllTrainingsForDedicatedEndTime(Date endTime);
     Training createTraining(TrainingRequest trainingRequest);
    Training update(Long trainingId, Training training);
    List<Training> getAllTrainingsForActivity(ActivityType activityType);
    }


