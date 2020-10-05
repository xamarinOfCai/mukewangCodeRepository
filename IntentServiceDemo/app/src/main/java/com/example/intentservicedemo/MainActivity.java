package com.example.intentservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button service = findViewById(R.id.service);
        Button intentService = findViewById(R.id.intentService);
        service.setOnClickListener(this);
        intentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.service:
                this.startNormalService();
                break;
            case R.id.intentService:
                this.startIntentService();
                break;
            default:
                break;
        }
    }

    private void startIntentService() {
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);
    }

    private void startNormalService() {
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }
}