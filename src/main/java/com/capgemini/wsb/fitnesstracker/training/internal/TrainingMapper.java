package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;
    TrainingDto toDto(Training training) {

        TrainingDto trainingDto= new TrainingDto();
        trainingDto.setId(training.getId());
        trainingDto.setUser(userMapper.toDto(training.getUser()));
        trainingDto.setAverageSpeed(training.getAverageSpeed());
        trainingDto.setDistance(training.getDistance());
        trainingDto.setStartTime(training.getStartTime());
        trainingDto.setEndTime(training.getEndTime());
        return trainingDto;
    }
    Training toEntity(TrainingDto trainingDto) {
        Training training = new Training();
        training.setId(trainingDto.getId());
        training.setUser(userMapper.toEntity(trainingDto.getUser()));
        training.setAverageSpeed(trainingDto.getAverageSpeed());
        training.setDistance(trainingDto.getDistance());
        training.setStartTime(trainingDto.getStartTime());
        training.setEndTime(trainingDto.getEndTime());
        return training;
    }



}
