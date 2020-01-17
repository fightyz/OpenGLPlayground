package com.fightyz.comlib;

import android.app.Application;

import timber.log.Timber;

/**
 * @author joe.yang@dji.com
 * @date 2020-01-15 14:23
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
    }

    void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }
}
