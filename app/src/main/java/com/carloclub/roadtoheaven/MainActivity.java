package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        ImageView Sokolka = findViewById(R.id.imageSokolka);
        Sokolka.setOnClickListener(v ->{
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

        Intent i = new Intent(MainActivity.this, DialogActivity.class);
        i.putExtra("videoPath", "android.resource://" + getPackageName() + "/" + R.raw.hello);
        //i.putExtra("CityName", "Sokolka");
        startActivityForResult(i,0);
    }
}