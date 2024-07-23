package com.javatutorialshub.asynctasks.merge;

import com.javatutorialshub.asynctasks.card.Card;
import com.javatutorialshub.asynctasks.user.User;

public record MergedUser(
        User user, Card card
) {
}
