package com.javatutorialshub.asynctasks.user;

import java.util.Date;

public record User(
        String id, String firstName, String lastName, String gender, String address,
        String city, String phone, String email, String status, Date createdDate
) {
}
