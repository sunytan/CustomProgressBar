package com.young.customprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.young.customprogressbar.view.HoritationProgressBar;

public class MainActivity extends AppCompatActivity {


    private HoritationProgressBar horitation_progress;

    private int progress = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            horitation_progress.setProgress(++progress);
            sendEmptyMessageDelayed(0x01,200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horitation_progress = findViewById(R.id.horitation_progress);
        handler.sendEmptyMessage(0x01);
    }
}
