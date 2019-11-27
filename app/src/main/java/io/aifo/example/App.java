package io.aifo.example;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;


public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
