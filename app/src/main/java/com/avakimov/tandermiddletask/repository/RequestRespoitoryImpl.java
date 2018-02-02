package com.avakimov.tandermiddletask.repository;

import com.avakimov.tandermiddletask.local.TanderMiddleDAO;

import java.util.concurrent.Executor;

/**
 * Created by Andrew on 31.01.2018.
 */

public class RequestRespoitoryImpl implements RequestRepository {
    private TanderMiddleDAO dao;
    private Executor executor;

}
