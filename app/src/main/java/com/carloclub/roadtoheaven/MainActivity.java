package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    TimerStart timerDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start);

        getSupportActionBar().hide();
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );


        timer = new Timer();
        timerDown = new TimerStart();
        timer.schedule(timerDown, 3000, 3000);


    }


    class TimerStart extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(() -> {

                timerDown.cancel();
                timerDown = null;

                Intent start = new Intent(MainActivity.this, DialogActivity.class);
                start.putExtra("videoPath", "android.resource://" + getPackageName() + "/" + R.raw.hello);
                //i.putExtra("CityName", "Sokolka");
                startActivityForResult(start,0);

                setContentView(R.layout.activity_main);

                ImageView sokolka = findViewById(R.id.imageSokolka);
                sokolka.setOnClickListener(v ->{
                    Intent i = new Intent(MainActivity.this, MapActivity.class);
                    i.putExtra("Head", "Сокулка");
                    i.putExtra("CityName", "Sokolka");
                    startActivityForResult(i,0);
                });

                ImageView imageViewMinus = findViewById(R.id.imageViewMinus);
                imageViewMinus.setOnClickListener(v ->{
                    Constants.DATAGAME.SCALE = Constants.DATAGAME.SCALE-15;
                });

                ImageView imageViewPlus = findViewById(R.id.imageViewPlus);
                imageViewPlus.setOnClickListener(v ->{
                    Constants.DATAGAME.SCALE = Constants.DATAGAME.SCALE+15;
                });

            });
        }

    }
}