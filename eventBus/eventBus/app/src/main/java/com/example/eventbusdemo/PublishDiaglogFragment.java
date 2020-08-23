package com.example.eventbusdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.example.eventbusdemo.event.AsyncEvent;
import com.example.eventbusdemo.event.BackGroundEvent;
import com.example.eventbusdemo.event.FailureEvent;
import com.example.eventbusdemo.event.MainEvent;
import com.example.eventbusdemo.event.MainOrderedEvent;
import com.example.eventbusdemo.event.PostingEvent;
import com.example.eventbusdemo.event.SuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PublishDiaglogFragment extends DialogFragment {

   public static final String TAG = "PublishFragmentBus";

    public static final String PUBISH_DIAGLOG_FRAGMENT = "PubishDiaglogFragment";

    public PublishDiaglogFragment(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Publish");
        final String[] items = {"Success","Failure","Posting","Main","MainOrdered","BackGround","Async"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //注意这里发送广播的时候，需要将Action的名字填写上

                switch (which){
                    case 0://success
                        EventBus.getDefault().post(new SuccessEvent(Thread.currentThread().toString()));

                        break;

                    case 1://fail
                    {

                        EventBus.getDefault().post(new FailureEvent(Thread.currentThread().toString()));
                    }
                        break;
                    case 2://Posting
                    {
                        postingModeEvent();

                    }
                    break;
                    case 3://Main
                    {
                        mainModeEvent();

                    }
                    break;
                    case 4://MainOrdered
                    {
                        mainOrderedModeEvent();

                    }
                    break;
                    case 5://Backgroud
                    {
                        backgroudModeEvent();

                    }
                    break;
                    case 6://Async
                    {
                        asyncModeEvent();

                    }
                    break;
                    default:
                        break;
                }
            }
        });
        return builder.create();
    }

    private void asyncModeEvent() {
        if (Math.random() > .5) {
            final ExecutorService pool = Executors.newFixedThreadPool(1);
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new AsyncEvent(Thread.currentThread().toString()));
                }
            });
            pool.shutdown();
        } else {
            EventBus.getDefault().post(new AsyncEvent(Thread.currentThread().toString()));
        }


    }

    private void backgroudModeEvent() {

        if(Math.random() > .5){
            final ExecutorService pool = Executors.newFixedThreadPool(1);
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new BackGroundEvent(Thread.currentThread().getId()+""));
                }
            });
            pool.shutdown();
        }else{
            EventBus.getDefault().post(new BackGroundEvent(Thread.currentThread().getId()+""));
        }

    }

    @SuppressLint("LongLogTag")
    private void mainOrderedModeEvent() {
        Log.d(TAG,"mainOrderedModeEvent begin"+new Date());
        EventBus.getDefault().post(new MainOrderedEvent(Thread.currentThread().toString()));
        Log.d(TAG,"mainOrderedModeEvent end"+new Date());
    }

    private void mainModeEvent() {
        Log.d(TAG,"mainModeEvent begin"+new Date());
        if(Math.random() > .5){
            new Thread(new Runnable() {
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    Log.d(TAG,"mainModeEvent new  thread"+new Date());
                    EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
                }
            }).start();
        }else{
            Log.d(TAG,"mainModeEvent main thread"+new Date());
            EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
        }
        Log.d(TAG,"mainModeEvent end"+new Date());

    }

    private void postingModeEvent() {
        if (Math.random() >.5) {
            final String threadInfo = Thread.currentThread().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(PUBISH_DIAGLOG_FRAGMENT,threadInfo+"大大于0.5");
                    EventBus.getDefault().post(new PostingEvent(threadInfo));
                }
            }).start();
        }else{
            final String threadInfo = Thread.currentThread().toString();
            Log.d(PUBISH_DIAGLOG_FRAGMENT,threadInfo+"小于0.5");
            EventBus.getDefault().post(new PostingEvent(threadInfo));
        }
    }
}
