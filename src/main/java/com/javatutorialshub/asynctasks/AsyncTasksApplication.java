package com.javatutorialshub.asynctasks;

import com.javatutorialshub.asynctasks.merge.UserDataMergerException;
import com.javatutorialshub.asynctasks.merge.UserDataMergerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class AsyncTasksApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTasksApplication.class);

    private final UserDataMergerService userDataMergerService;
    @Value("${user.data.file.path}")
    private String userDataFilePath;
    @Value("${card.data.file.path}")
    private String cardDataFilePath;
    @Value("${output.file.path}")
    private String outputFilePath;

    public static void main(String[] args) {
        SpringApplication.run(AsyncTasksApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            userDataMergerService.merge(userDataFilePath, cardDataFilePath, outputFilePath);
        } catch (UserDataMergerException e) {
            logger.error(e.getMessage(), e);
            System.exit(-1);
        }
    }
}
