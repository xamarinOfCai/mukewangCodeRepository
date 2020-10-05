package com.example.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {


    private static final String TAG = "MyIntentServiceAndService";


    public MyIntentService() {
        super("myIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        long endTime = System.currentTimeMillis()+25*1000;
        Log.d(TAG,"onHandleIntent  begin");

        while(System.currentTimeMillis() < endTime){
            synchronized (this){
                try {
                    wait(endTime-System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG,"onHandleIntent end");

    }
}
