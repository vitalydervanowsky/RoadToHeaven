package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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

    ImageView imageViewWallpaper; //Карта
    ImageView car;
     int progressCar=0;
    private MediaPlayer rrrMediaPlayer;
    private MediaPlayer bipMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start);
        rrrMediaPlayer = MediaPlayer.create(this, R.raw.rrr);
        bipMediaPlayer = MediaPlayer.create(this, R.raw.bip);

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
        timer.schedule(timerDown, 3000, 30);


    }


    class TimerStart extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(() -> {
                if (progressCar==0){
                    imageViewWallpaper =findViewById(R.id.imageViewWallpaper);
                    car = findViewById(R.id.car);
                    imageViewWallpaper.setImageResource(0);
                    car.setImageDrawable(getDrawable(R.drawable.caranimation));
                    ((AnimationDrawable)car.getDrawable()).start();
                    rrrMediaPlayer.start();
                }
                if (progressCar==80){
                    bipMediaPlayer.start();
                }
                if (progressCar==100){
                    imageViewWallpaper.setImageResource(R.drawable.wallpaper1);
                }
                if (progressCar==130){
                    bipMediaPlayer.stop();
                }
                if (progressCar<220){
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
                    int newX=params.leftMargin+8;
                    int newY=params.topMargin;
                    params.setMargins(newX,newY,0,0);
                    car.setLayoutParams(params);

                    progressCar++;
                    return;
                }

                if (progressCar==220){
                    rrrMediaPlayer.stop();
                    car.setVisibility(View.INVISIBLE);
                }
                if (progressCar<400){
                    imageViewWallpaper.setImageResource(R.drawable.slide1);
                    progressCar++;
                    return;
                }

                if (progressCar<500){
                    imageViewWallpaper.setImageResource(R.drawable.slide2);
                    progressCar++;
                    return;
                }

                if (progressCar<600){
                    imageViewWallpaper.setImageResource(R.drawable.slide3);
                    progressCar++;
                    return;
                }

                if (progressCar<700){
                    imageViewWallpaper.setImageResource(R.drawable.slide4);
                    progressCar++;
                    return;
                }

                if (progressCar<800){
                    imageViewWallpaper.setImageResource(R.drawable.slide5);
                    progressCar++;
                    return;
                }

                timerDown.cancel();
                timerDown = null;
                car=null;

                rrrMediaPlayer.pause();

//                Intent start = new Intent(MainActivity.this, DialogActivity.class);
//                start.putExtra("videoPath", "android.resource://" + getPackageName() + "/" + R.raw.hello);
//                //i.putExtra("CityName", "Sokolka");
//                startActivityForResult(start,0);

                setContentView(R.layout.activity_main);

                ImageView sokolka = findViewById(R.id.imageSokolka);
                sokolka.setOnClickListener(v ->{
                    Intent i = new Intent(MainActivity.this, MapActivity.class);
                    i.putExtra(Constants.CITY_ARG, City.SOKULKA);
                    startActivityForResult(i,0);
                });



            });
        }

    }
}