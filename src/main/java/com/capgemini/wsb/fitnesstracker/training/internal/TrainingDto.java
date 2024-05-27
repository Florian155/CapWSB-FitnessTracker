package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Data
@Getter
@Setter
@NoArgsConstructor
class TrainingDto{

    private Long id;


    private UserDto user;

    private Date startTime;

    private Date endTime;


    private ActivityType activityType;

    private double distance;


    private double averageSpeed;



}


