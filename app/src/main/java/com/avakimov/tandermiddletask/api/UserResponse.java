package com.avakimov.tandermiddletask.api;

import com.avakimov.tandermiddletask.domain.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 02.02.2018.
 */

public class UserResponse {
    @SerializedName("data")
    public User user;
}
