//    package com.capgemini.wsb.fitnesstracker.notification;
//
//    import lombok.AllArgsConstructor;
//    import lombok.extern.slf4j.Slf4j;
//    import org.springframework.mail.SimpleMailMessage;
//    import org.springframework.mail.javamail.JavaMailSender;
//    import org.springframework.scheduling.annotation.EnableScheduling;
//    import org.springframework.scheduling.annotation.Scheduled;
//    import org.springframework.stereotype.Component;
//
//    @EnableScheduling
//    @Component
//    @AllArgsConstructor
//    @Slf4j
//    public class ReportGenerator {
//        JavaMailSender javaMailSender;
//        @Scheduled(fixedDelay = 10000, initialDelay = 1000)
//        public void logToStdOut()
//
//        {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("your-email@example.com");
//            message.setTo("recipient@example.com");
//            message.setSubject("Subject of the email");
//            message.setText("Content of the email");
//
//            log.info("Report generated");
//            javaMailSender.send(message);
//        }
//    }
