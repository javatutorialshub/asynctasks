package com.javatutorialshub.asynctasks.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class UserDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(UserDataLoader.class);
    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");


    @Async
    public CompletableFuture<Collection<User>> loadAll(String userDataFilePath) throws UserDataLoaderException {
        try {
            logger.info("Starting loading user data from: {}", userDataFilePath);
            List<String> lines = Files.readAllLines(Path.of(userDataFilePath));
            return CompletableFuture.completedFuture(
                    lines.subList(1, lines.size()).stream().map(l -> {
                        String[] values = l.split(",");
                        try {
                            return new User(
                                    values[0].replaceAll("\"", ""),
                                    values[1].replaceAll("\"", ""),
                                    values[2].replaceAll("\"", ""),
                                    values[3].replaceAll("\"", ""),
                                    values[4].replaceAll("\"", ""),
                                    values[5].replaceAll("\"", ""),
                                    values[6].replaceAll("\"", ""),
                                    values[7].replaceAll("\"", ""),
                                    values[8].replaceAll("\"", ""),
                                    dateTimeFormatter.parse(values[9].replaceAll("\"", ""))

                            );
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList()
            );
         } catch (IOException e) {
            logger.warn("unable to load user data when trying to read file: {}", userDataFilePath);
            throw new UserDataLoaderException(e);
        } finally {
            logger.info("Ending loading user data from: {}", userDataFilePath);
        }
    }
}
