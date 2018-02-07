package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;

import com.avakimov.tandermiddletask.domain.User;

import java.util.List;

/**
 * Created by Andrew on 01.02.2018.
 */

public interface RequestRemoteRepository {
    LiveData<List<User>> getUsersByNamePart(String value);
    void setCustomToken(String token);
}
