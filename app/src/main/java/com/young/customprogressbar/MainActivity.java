package com.young.customprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.young.customprogressbar.view.CycleProgressBar;
import com.young.customprogressbar.view.HoritationProgressBar;

public class MainActivity extends AppCompatActivity {


    private HoritationProgressBar horitation_progress;
    private CycleProgressBar cycleProgressBar;

    private int progressH = 0;
    private int progressC = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    horitation_progress.setProgress(++progressH);
                    sendEmptyMessageDelayed(0x01, 200);
                    if (progressH > horitation_progress.getMax())
                        removeMessages(0x01);
                break;
                case 0x02:
                    cycleProgressBar.setProgress(++progressC);
                    sendEmptyMessageDelayed(0x02,200);
                    if (progressC > cycleProgressBar.getMax())
                        removeMessages(0x02);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horitation_progress = findViewById(R.id.horitation_progress);
        cycleProgressBar = findViewById(R.id.cycle_progress);
        handler.sendEmptyMessage(0x02);
        handler.sendEmptyMessage(0x01);
    }
}
