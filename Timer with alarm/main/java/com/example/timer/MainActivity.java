package com.example.timer;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView timer;
    int minutes, seconds;
    SeekBar MinSeekBar;
    boolean timerIsRunning = false;
    CountDownTimer cdtimer;
    Button goButton;
    ImageView egg;
    Animation anim;
    MediaPlayer mplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        egg = (ImageView) findViewById(R.id.egg);
        goButton = findViewById(R.id.start);
        timer = (TextView) findViewById(R.id.timer);
        timer.setText("00:30");
        MinSeekBar = findViewById(R.id.seekBarMin);
        MinSeekBar.setMax(500);
        MinSeekBar.setProgress(30);
        MinSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = progress / 60;
                int sec = progress - (min * 60);
                if (progress < 0) {
                    timer.setText("00:00");
                } else {
                    timer.setText("" + min + ":" + sec);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        //Checks if the countdown timer is already running
        if (timerIsRunning) {
            initTimer();

        } else {
            mplayer = MediaPlayer.create(getApplicationContext(), R.raw.tic_tac_reveil);     //Tick-Tock sound
            mplayer.setLooping(true);
            mplayer.start();
            anim = AnimationUtils.loadAnimation(this, R.anim.swing);
            egg.startAnimation(anim);
            view.animate().alpha(0.2f).setDuration(2000);
            timerIsRunning = true;
            MinSeekBar.setEnabled(false);
            goButton.setText("Stop!");
            minutes = MinSeekBar.getProgress() / 60;
            seconds = MinSeekBar.getProgress() - (minutes * 60);
            cdtimer = new CountDownTimer((minutes * 60 + seconds) * 1000 + 100, 1000) {
                @Override

                //Progress of the countdown timer
                public void onTick(long millisUntilFinished) {
                    seconds -= 1;
                    String secondString = Integer.toString(seconds);
                    if (seconds <= 9 && seconds >= 0) {
                        secondString = "0" + secondString;
                    }
                    if (seconds < 0) {
                        minutes -= 1;
                        seconds = 59;
                        secondString = Integer.toString(seconds);
                    }
                    timer.setText(Integer.toString(minutes) + ":" + secondString);
                }

                @Override
                public void onFinish() {
                    initTimer();
                    Toast.makeText(MainActivity.this, "We finished!!", Toast.LENGTH_SHORT).show();
                    mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);     //KUKURIKUUUUUU
                    mplayer.start();

                }
            }.start();
        }
    }

    //set the app to its initial stat
    public void initTimer() {
        MinSeekBar.setEnabled(true);
        anim.cancel();
        mplayer.stop();
        mplayer.release();
        egg.clearAnimation();
        cdtimer.cancel();
        goButton.setText("Start!");
        timerIsRunning = false;
        goButton.animate().alpha(1).setDuration(2000);
        MinSeekBar.setProgress(30);
        timer.setText("00:30");
    }
}
