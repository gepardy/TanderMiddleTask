package com.avakimov.tandermiddletask.util;

/**
 * Created by Andrew on 03.02.2018.
 */

public class NetworkState {
    private final Status status;
    private final String message;

    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, "");
    public static final NetworkState LOADING = new NetworkState(Status.RUNNING, "");


    public NetworkState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
