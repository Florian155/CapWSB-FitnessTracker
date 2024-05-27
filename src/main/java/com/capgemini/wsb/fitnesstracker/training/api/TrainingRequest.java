package com.capgemini.wsb.fitnesstracker.training.api;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@Data
@Getter
@Setter
@NoArgsConstructor
public class TrainingRequest {

        private Long userId;



        private Date startTime;

        private Date endTime;


        private ActivityType activityType;

        private double distance;


        private double averageSpeed;


    }

