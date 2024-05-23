package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
public class TrainingDto{

    private Long id;


    private UserDto user;

    private Date startTime;

    private Date endTime;


    private ActivityType activityType;

    private double distance;


    private double averageSpeed;


}


