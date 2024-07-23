package com.javatutorialshub.asynctasks.card;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Component
public class CardDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(CardDataLoader.class);

    @Async
    public CompletableFuture<Collection<Card>> loadAll(String cardFilePath) throws CardDataLoaderException {
        try {
            logger.info("Starting loading card data from: {}", cardFilePath);
            Card[] cards = new Gson().fromJson(new FileReader(cardFilePath), Card[].class);
            return CompletableFuture.completedFuture(Arrays.stream(cards).toList());
        } catch (IOException e) {
            logger.warn("unable to load card data when trying to load json file: {}", cardFilePath);
            throw new CardDataLoaderException(e);
        } finally {
            logger.info("Ending loading card data from: {}", cardFilePath);
        }
    }
}
