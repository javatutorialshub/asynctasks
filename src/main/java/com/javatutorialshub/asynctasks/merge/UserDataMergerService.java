package com.javatutorialshub.asynctasks.merge;

import com.google.gson.GsonBuilder;
import com.javatutorialshub.asynctasks.card.Card;
import com.javatutorialshub.asynctasks.card.CardDataLoader;
import com.javatutorialshub.asynctasks.card.CardDataLoaderException;
import com.javatutorialshub.asynctasks.user.User;
import com.javatutorialshub.asynctasks.user.UserDataLoader;
import com.javatutorialshub.asynctasks.user.UserDataLoaderException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDataMergerService {
    private static final Logger logger = LoggerFactory.getLogger(UserDataMergerService.class);

    private final UserDataLoader userDataLoader;
    private final CardDataLoader cardDataLoader;

    public void merge(String userDataFilePath, String cardDataFile, String outputResultFileName) throws UserDataMergerException {
        try {
            CompletableFuture<Collection<User>> userDataFuture = userDataLoader.loadAll(userDataFilePath);
            CompletableFuture<Collection<Card>> cardFuture = cardDataLoader.loadAll(cardDataFile);

            CompletableFuture.allOf(userDataFuture, cardFuture);

            Collection<User> users = userDataFuture.get();
            Collection<Card> cards = cardFuture.get();

            Map<String, Card> cardMap = cards.stream().collect(Collectors.toMap(Card::userId, Function.identity(), (k1, k2) -> k1));
            Collection<MergedUser> mergedUsers = users.stream().map(u -> new MergedUser(u, cardMap.get(u.id()))).toList();
            new GsonBuilder().setPrettyPrinting().create().toJson(mergedUsers.toArray(new MergedUser[0]), MergedUser[].class, new FileWriter(outputResultFileName));

        } catch (UserDataLoaderException |  CardDataLoaderException e) {
            throw new UserDataMergerException(e);
        } catch (Throwable e) {
            logger.warn("unable to merge user data in a resulting file: {}", outputResultFileName);
            throw new UserDataMergerException(e);
        } finally {
            logger.info("--> End merging file data");
        }
    }
}
