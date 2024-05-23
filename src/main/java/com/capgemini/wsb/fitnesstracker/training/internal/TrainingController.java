package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor

public class TrainingController {
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
    @GetMapping("/{endTime}")
    public   List<TrainingDto> getTrainingByEndTime(Date endTime){
        return trainingService.getAllTrainingsForDedicatedEndTime(endTime).stream().map(trainingMapper::toDto).toList();
    }}


