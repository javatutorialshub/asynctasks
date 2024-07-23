package com.javatutorialshub.asynctasks.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LongTaskComputer {

    private static final Logger logger = LoggerFactory.getLogger(LongTaskComputer.class);

    @Async
    public void compute(){
        logger.info("Starting long task");
        try {
            Thread.sleep(2000);
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
        }
        logger.info("Ending long task");
    }
}