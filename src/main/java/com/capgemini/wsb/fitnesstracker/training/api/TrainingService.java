package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<Training> getAllTrainingsForDedicatedUser(Long userId);
    List<Training> getAllTrainingsForDedicatedEndTime(Date endTime);

}


