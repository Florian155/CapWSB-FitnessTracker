    package com.capgemini.wsb.fitnesstracker.notification;

    import com.capgemini.wsb.fitnesstracker.training.api.Training;
    import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
    import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
    import com.capgemini.wsb.fitnesstracker.user.api.User;
    import com.capgemini.wsb.fitnesstracker.user.api.UserService;
    import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
    import lombok.AllArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.mail.javamail.JavaMailSender;
    import org.springframework.scheduling.annotation.EnableScheduling;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Component;

    import java.time.LocalDateTime;
    import java.util.List;

    @EnableScheduling
    @Component
    @AllArgsConstructor
    @Slf4j
    public class ReportGenerator {
        private final JavaMailSender javaMailSender;
        private final UserService userService;
        private final TrainingService trainingService;

        @Scheduled(cron = "0 0 0 * * MON")
        public void generateAndSendWeeklyReports() {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                List<Training> trainings = trainingService.getAllTrainingsForDedicatedUser(user.getId());
                int trainingCount = trainings.size();
                sendWeeklyReport(user, trainingCount);
            }
        }

        private void sendWeeklyReport(User user, int trainingCount) {
            String subject = "Tygodniowy raport treningów";
            String body = String.format("Drogi %s %s,\n\nmasz zarejestrowane łącznie %d treningów..\n\nPozdrawiamy,\nTwój Zespół Fitness Tracker", user.getFirstName(), user.getLastName(), trainingCount);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("your-email@example.com");
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(body);

            log.info("Wysłano raport do użytkownika: " + user.getEmail());
            javaMailSender.send(message);
        }
    }
