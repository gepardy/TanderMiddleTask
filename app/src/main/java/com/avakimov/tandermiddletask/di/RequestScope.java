package com.avakimov.tandermiddletask.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Andrew on 04.02.2018.
 */

@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RequestScope {
}
