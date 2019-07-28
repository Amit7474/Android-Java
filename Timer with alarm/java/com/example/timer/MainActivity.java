package com.example.timer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView egg = (ImageView) findViewById(R.id.egg);
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
        if (timerIsRunning) {
            view.animate().alpha(1).setDuration(2000);
            timer. setText("00:30");
            MinSeekBar.setProgress(30);
            MinSeekBar.setEnabled(true);
            cdtimer.cancel();
            goButton.setText("Start!");
            timerIsRunning = false;
        } else {
            view.animate().alpha(0.2f).setDuration(2000);
            timerIsRunning = true;
            MinSeekBar.setEnabled(false);
            goButton.setText("Stop!");
            minutes = MinSeekBar.getProgress() / 60;
            seconds = MinSeekBar.getProgress() - (minutes * 60);
            cdtimer = new CountDownTimer((minutes * 60 + seconds) * 1000 + 100, 1000) {
                @Override
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
                    Toast.makeText(MainActivity.this, "We finished!!", Toast.LENGTH_SHORT).show();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();
                }
            }.start();
        }
    }
}
