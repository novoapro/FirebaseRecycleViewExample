package com.manpdev.firebaseexample.global;

import android.app.Application;

import com.firebase.client.Config;
import com.firebase.client.Firebase;

/**
 * Created by FirebaseExample novoa on 3/18/16.
 */
public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
