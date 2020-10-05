package com.example.diglettgame2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private final static int TYPEINT = 123;;
    public static final int DELAYTIME = 1000;
    private ImageView diglettIv;
    private Button startBtn;
    private TextView decriptionView;

    public int[][] mPosition = new  int[][]{
            {342,180},{432,880},
            {521,256},{429,780},
            {456,976},{145,665},
            {123,678},{564,567},
    };
    private  int mTotalCount;
    private  int mSuccessCount;

    public static final int MAX_COUNT = 10;
    private MyHandler myHandler = new MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diglettIv = this.findViewById(R.id.diglettIv);
        startBtn = this.findViewById(R.id.startBtn);
        decriptionView = findViewById(R.id.scoreDecription);
        startBtn.setOnClickListener(this);
        //myHandler = new MyHandler(this);
        diglettIv.setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtn:
                startGame();
                break;
            default:
                break;
        }
    }

    private void startGame() {
        decriptionView.setText("开始游戏");
        startBtn.setText("游戏中。。。。");
        startBtn.setEnabled(false);
        next(0);
    }

    private void next(int delayTime) {
        int position = new Random().nextInt(mPosition.length);

        Message message = Message.obtain();
        message.what = TYPEINT;
        message.arg1 = position;

        myHandler.sendMessageDelayed(message,delayTime);
        mTotalCount++;

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        diglettIv.setVisibility(View.GONE);
        mSuccessCount++;
        decriptionView.setText("达到了"+mSuccessCount+"只，共"+MAX_COUNT+"只");
        return false;
    }

    public void clear(){
        mSuccessCount = 0;
        mTotalCount = 0;
        diglettIv.setVisibility(View.GONE);
        startBtn.setEnabled(true);
        startBtn.setText(R.string.点击开始);
    }


    static class MyHandler extends Handler{
        private WeakReference<MainActivity>mainActivity;



        public MyHandler(MainActivity mainActivity) {
            this.mainActivity = new WeakReference<MainActivity>(mainActivity);
        }

        /**
         * @param msg
         */
        @Override
        public void handleMessage(@NonNull Message msg) {
            MainActivity activity = mainActivity.get();
            switch (msg.what){
                case TYPEINT:
                    if(activity.mSuccessCount >= MainActivity.MAX_COUNT){
                        activity.clear();
                        Toast.makeText(activity,"地鼠打完了",Toast.LENGTH_LONG).show();
                        return ;
                    }
                    int position = msg.arg1;
                    activity.diglettIv.setX(activity.mPosition[position][0]);
                    activity.diglettIv.setY(activity.mPosition[position][1]);
                    activity.diglettIv.setVisibility(View.VISIBLE);

                    int randomTime = new Random().nextInt(DELAYTIME)+DELAYTIME;
                    activity.next(randomTime);
                    break;
                default:
                    break;
            }
        }
    }
}