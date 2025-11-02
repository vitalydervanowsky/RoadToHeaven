package com.carloclub.roadtoheaven;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import java.util.Timer;
import java.util.TimerTask;

public class DialogActivity extends AppCompatActivity {
    Timer timer;
    TimerStart timerDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_dialog);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = getIntent().getStringExtra("videoPath");//"android.resource://" + getPackageName() + "/" + R.raw.hello;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        TextView button = findViewById(R.id.button);
        button.setOnClickListener(v ->{
            finish();
        });

    }

    class TimerStart extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(() -> {
                timerDown.cancel();
                timerDown = null;


            });
        }
    }
}
