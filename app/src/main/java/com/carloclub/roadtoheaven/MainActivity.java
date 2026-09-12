package com.carloclub.roadtoheaven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.carloclub.roadtoheaven.databases.Mission;
import com.carloclub.roadtoheaven.databases.RthBase;
import com.carloclub.roadtoheaven.helper.CityHelper;
import com.carloclub.roadtoheaven.maps.City;
import com.carloclub.roadtoheaven.maps.CityAdapter;
import com.carloclub.roadtoheaven.maps.MapCity;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    TimerStart timerDown;
    TimerLoad timerLoad;

    ImageView imageViewWallpaper; //Карта
    ImageView car;
     int progressCar=0;
     int selectedMap;
    private MediaPlayer rrrMediaPlayer;
    private MediaPlayer bipMediaPlayer;

    int allQueryes=0;
    ArrayList<String> steps = new ArrayList<String>();
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
        timer.schedule(timerDown, 2000, 30);

        findViewById(R.id.nextButton).setOnClickListener(v -> {
            progressCar=progressCar+100;
        });

        RthBase.getInstance(getApplicationContext());
        FirebaseApp.initializeApp(this);
        Firebase.getTable("Images", this);
        Firebase.getTable("Mission", this);

    }

    public void startLoadMap(){
        Firebase.startedQueryes.clear();
        Firebase.startedProblems.clear();
        setContentView(R.layout.dialog_start);
        Mission m = RthBase.instance.missionDao().getById(selectedMap);
        Firebase.updateImage(m.mapImageId);
        Firebase.getTable("Lesson", this, "mapID", String.valueOf(m.id), 0L); //m.dateActual
        Firebase.getTable("Gallery", this, "mapID", String.valueOf(m.id), 0L);
        Firebase.getTable("Questions", this, "Level", String.valueOf(m.level), 0L);  //специальные вопросы для этой миссии  //m.dateActual
        Firebase.getTable("Questions", this, "Level", "0", 0L); //и общие вопросы без привязки к мисии //m.dateActual
        Firebase.getTable("MissionQuestions", this, "mapID", String.valueOf(m.id), 0L); //распределение вопросов по объектам к мисии //m.dateActual

        Firebase.getTable("MapCells", this, "mapID", String.valueOf(m.id), 0L); //m.dateActual
        Firebase.getTable("DataWords", this, "mapID", String.valueOf(m.id), 0L); //m.dateActual

        progressCar=0;
        allQueryes = Firebase.startedQueryes.size();

        timer = new Timer();
        timerLoad = new TimerLoad();
        timer.schedule(timerLoad, 100, 50);
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
                    rrrMediaPlayer.pause();
                    car.setVisibility(View.INVISIBLE);
                    findViewById(R.id.nextButton).setVisibility(View.VISIBLE);
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

                List<MapCity> cities = CityHelper.INSTANCE.getAllCities();
                CityAdapter cityAdapter = new CityAdapter(RthBase.instance.missionDao().getAllMissions(), mapCity -> {
                    selectedMap=mapCity.id;
                    startLoadMap();
//                    Intent i = new Intent(MainActivity.this, MapActivity.class);
//                    i.putExtra(Constants.CITY_ARG, mapCity.getCity());
//                    startActivityForResult(i,0);
                    return null;
                });
                ((RecyclerView) findViewById(R.id.recyclerView)).setAdapter(cityAdapter);
            });
        }

    }

    class TimerLoad extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(() -> {
                if (timerLoad == null) return;

                if (progressCar==0){
                    imageViewWallpaper =findViewById(R.id.imageViewWallpaper);
                    car = findViewById(R.id.car);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
                    int newX=-100;
                    int newY=params.topMargin;
                    params.setMargins(newX,newY,0,0);
                    car.setLayoutParams(params);

                    car.setVisibility(View.VISIBLE);
                    imageViewWallpaper.setImageResource(0);
                    car.setImageDrawable(getDrawable(R.drawable.caranimation));
                    ((AnimationDrawable)car.getDrawable()).start();
                    rrrMediaPlayer.start();
                }

                int wp = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
                int s=Firebase.startedQueryes.size();
                int targetX = (int)((allQueryes-s)*(wp-300)/allQueryes)+300;

                if (car==null) {     //progressCar<220 &&
                    return;
                }

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) car.getLayoutParams();
                //Медленно катимся от текущей Х К targetX. Делим расстояние на 10, чтобы за секунду доехать, т.е. замедляемся
                int step = (int)((targetX-params.leftMargin)/15); //Замедляемся
                if (step>10) step=10;

                if (params.leftMargin==targetX) step=0;
                else if (targetX-params.leftMargin<100) step=1;
                else step=2;


                if (s==0)
                    step = 15; //на последнем ускоряемся


                int newX=params.leftMargin+step;
                int newY=params.topMargin;
                params.setMargins(newX,newY,0,0);
                car.setLayoutParams(params);

                steps.add(String.valueOf(newX));
                progressCar++;


                if (Firebase.startedQueryes.size()==0 && newX>=wp){
                    rrrMediaPlayer.pause();
                    car.setVisibility(View.INVISIBLE);
                    timerLoad.cancel();
                    timerLoad = null;
                    car=null;
                    setContentView(R.layout.activity_main);

                    //если все данные загружены успешно, обновляем дату актуальности
                    if (Firebase.startedProblems.size()==0) {
                        Mission m = RthBase.instance.missionDao().getById(selectedMap);
                        m.dateActual = System.currentTimeMillis();
                        RthBase.instance.missionDao().upsertMission(m);
                    }

                    //иначе: проверяем можно ли запустить миссию со старыми данными
                    //...

                    //запускаем игру

                    Intent i = new Intent(MainActivity.this, MapActivity.class);
                    i.putExtra(Constants.CITY_ARG, selectedMap);
                    startActivityForResult(i,0);
                    return;

                }



//                timerLoad.cancel();
//                timerLoad = null;
//                car=null;

                //rrrMediaPlayer.pause();


            });
        }

    }

}