package com.avakimov.tandermiddletask.util;

/**
 * Created by Andrew on 03.02.2018.
 */

public class NetworkState {
    private final Status status;
    private final String message;
    private final Boolean canRetry;

    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, "", null);
    public static final NetworkState LOADING = new NetworkState(Status.RUNNING, "", null);


    public NetworkState(Status status, String message, Boolean canRetry) {
        this.status = status;
        this.message = message;
        this.canRetry = canRetry;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getCanRetry() {
        return canRetry;
    }
}
