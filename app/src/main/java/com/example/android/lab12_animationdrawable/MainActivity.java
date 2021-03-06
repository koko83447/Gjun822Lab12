package com.example.android.lab12_animationdrawable;

import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView m_iv_duke;
    private AnimationDrawable m_animationDrawable;
    private TextView m_tv_message;
    
    private View m_view_logo;
    private TextView m_logo_name;
    private TextView m_view_message;
    
    private TypedArray mNbaLogos;
    private TypedArray mNbaNames;
    private int mNbaLogosCount;

    private Button m_btn_go;

//    private int mRadom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFrameAnimation();
        initNbaLogos();
    }

    private void initView() {
        m_iv_duke = (ImageView)findViewById(R.id.img_duke);
        m_tv_message = (TextView)findViewById(R.id.tv_message);

        m_view_logo = findViewById(R.id.view_logo);
        m_logo_name = (TextView)findViewById(R.id.tv_logo_name);
        m_view_message = (TextView)findViewById(R.id.view_message);

        m_btn_go = (Button)findViewById(R.id.btn_go);
    }

    private void initNbaLogos() {
        mNbaLogos = getNbaLogos();
        mNbaNames = getNbaNames();
        mNbaLogosCount = mNbaLogos.length();
//        mRadom = (int)(Math.random()*12);
        m_view_logo.setBackground(mNbaLogos.getDrawable(0));
    }

    public TypedArray getNbaLogos() {
        TypedArray logos = getResources().obtainTypedArray(R.array.nba_logos);
        return logos;
    }

    private void initFrameAnimation() {
        m_iv_duke.setBackgroundResource(R.drawable.frame_animation);
        m_animationDrawable = (AnimationDrawable)m_iv_duke.getBackground();
    }
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_start:
                m_animationDrawable.start();
                break;
            case R.id.btn_stop:
                m_animationDrawable.stop();
                break;
            case R.id.btn_5_secs:
                animation5secs();
                break;
        }
    }
    private Handler m_handler = new Handler();

    private void animation5secs() {
        int delatMillis = 5 * 1000;
        Runnable task = new Task();
        boolean result = m_handler.postDelayed(task,delatMillis);
        m_tv_message.setText(result ? "交付成功" : "交付失敗");
        m_animationDrawable.start();
    }

    public TypedArray getNbaNames() {
        TypedArray names = getResources().obtainTypedArray(R.array.nba_logo_name);
        return names;
    }
//
//    public void go(View view) {
//        mRadom = (int)(Math.random()*12);
//        m_view_logo.setBackground(mNbaLogos.getDrawable(mRadom));
//    }


    private class Task implements Runnable {
        @Override
        public void run(){
            m_animationDrawable.stop();
            m_tv_message.setText("時間到");
        }
    }

    public void go(View view){
        boolean result = m_handler.post(mStartRandomTask);
        m_handler.postDelayed(mStopRandomTask,3000);
        m_view_message.setText(result ? "post成功" : "post失敗");
        m_btn_go.setEnabled(false);
    }
    private Runnable mStartRandomTask = new StartRandomTask();
    private Runnable mStopRandomTask = new StopRandomTask();

    private class StartRandomTask implements Runnable {
        @Override
        public void run(){
            int index = (int)(Math.random()*mNbaLogosCount);
            m_view_logo.setBackground(mNbaLogos.getDrawable(index));
            m_logo_name.setText(mNbaNames.getText(index));
            m_handler.postDelayed(this, 100);
        }
    }
    private class StopRandomTask implements Runnable {
        @Override
        public void run(){
            m_handler.removeCallbacks(mStartRandomTask);
            m_btn_go.setEnabled(true);
            m_view_message.setText("時間到");
        }
    }
}
