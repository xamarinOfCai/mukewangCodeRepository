package com.example.eventbusdemo;

import android.os.Bundle;

import com.example.eventbusdemo.event.AsyncEvent;
import com.example.eventbusdemo.event.BackGroundEvent;
import com.example.eventbusdemo.event.FailureEvent;
import com.example.eventbusdemo.event.MainEvent;
import com.example.eventbusdemo.event.MainOrderedEvent;
import com.example.eventbusdemo.event.PostingEvent;
import com.example.eventbusdemo.event.SuccessEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity  {

    public static final String TAG ="PublishFragmentBus";
    public static final String BROAD_CAST_EVENT_BUS_DEMO = "BroadCast_EventBus_Demo";
    public static final String SUCCESS_FLAG = "SuccessFlag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Subscriber");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PublishDiaglogFragment fragment = new PublishDiaglogFragment();
                fragment.show(getSupportFragmentManager(),"Publisher");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setImageSrc(int imageSrc) {
        ImageView imageView = findViewById(R.id.emotionImageView);
        if (imageView != null) {
            imageView.setImageResource(imageSrc);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void successItemEvent(SuccessEvent event){
        setImageSrc(R.drawable.ic_happy_24);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void successItemEvent(FailureEvent event){
        setImageSrc(R.drawable.ic_bad_24);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void postingModeEvent(final PostingEvent event){
        final String threadInfo = Thread.currentThread().toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(event.getThreadInfo());
                setSubscriberThreadInfo(threadInfo);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainModeEvent(final MainEvent event) {
        Log.d(TAG,"MainActivity mainModeEvent begin"+ new Date());
        final String threadInfo = Thread.currentThread().toString();

        setPublisherThreadInfo(event.getThreadInfo());
        setSubscriberThreadInfo(threadInfo);
        Log.d(TAG,"MainActivity mainModeEvent end"+ new Date());
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void mainOrderedModeEvent(final MainOrderedEvent event) {
        Log.d(TAG,"MainActivity mainOrderedModeEvent begin"+ new Date());
        final String threadInfo = Thread.currentThread().toString();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setPublisherThreadInfo(event.getThreadInfo());
        setSubscriberThreadInfo(threadInfo);
        Log.d(TAG,"MainActivity mainOrderedModeEvent end"+ new Date());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEvent(final BackGroundEvent event) {
        Log.d(TAG, "主线程ID："+Looper.getMainLooper().getThread().getId()+" ");
        final String threadInfo = Thread.currentThread().getId()+" ";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(event.threadInfo);
                setSubscriberThreadInfo(threadInfo);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void asyncModeEvent(final AsyncEvent event) {
        Log.d(TAG, "主线程ID："+Looper.getMainLooper().getThread().getId()+" ");
        final String threadInfo = Thread.currentThread().getId()+" ";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setPublisherThreadInfo(event.threadInfo);
                setSubscriberThreadInfo(threadInfo);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setPublisherThreadInfo(String threadInfo) {
        setTextView(R.id.publisherThreadTextView, threadInfo);
    }

    private void setSubscriberThreadInfo(String threadInfo) {
        setTextView(R.id.subscriberThreadTextView, threadInfo);
    }

    /**
     * Should run in UI thread.
     *
     * @param resId
     * @param text
     */
    private void setTextView(int resId, String text) {
        final TextView textView = (TextView) findViewById(resId);
        textView.setText(text);
        textView.setAlpha(.5f);
        textView.animate().alpha(1).start();
    }
}