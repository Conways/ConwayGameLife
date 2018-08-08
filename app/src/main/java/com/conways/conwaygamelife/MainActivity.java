package com.conways.conwaygamelife;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int MSG_START = 677;


    private ConwayView cv;
    private Button btStart;
    private Button btStop;
    private Button btReset;

    private MyHandle mh;
    private boolean isLiving;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cv = findViewById(R.id.cv);
        btStart = findViewById(R.id.start);
        btStop = findViewById(R.id.stop);
        btReset = findViewById(R.id.reset);

        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btReset.setOnClickListener(this);

        mh = new MyHandle();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                isLiving=true;
                live(MSG_START);
                break;
            case R.id.stop:
                isLiving=false;
                break;
            case R.id.reset:
                isLiving=false;
                cv.reset();
                break;
            default:
                break;
        }

    }


    private void live(int msg) {
        mh.sendEmptyMessageDelayed(msg, 200l);
    }

    private class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_START&&isLiving) {
                cv.getnext();
                live(MSG_START);
            }

        }
    }
}
