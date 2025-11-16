package com.carloclub.roadtoheaven;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapObjects.MapObject;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Victorina {
    MapObject object;
    int userAnswer;
    boolean pause;
    View context;
    TextView buttonAnswer1;
    TextView buttonAnswer2;
    TextView buttonAnswer3;
    TextView buttonAnswer4;
    public MediaPlayer correctMediaPlayer;
    private MediaPlayer incorrectMediaPlayer;
    private MediaPlayer triumfMediaPlayer;
    int trueAnswer;
    String [] answers;
    Random random;
    Timer timer;
    TimerVictorina timerDown;


    public Victorina (MapObject forObject, View context){
        object=forObject;
        this.context=context;
        timer = new Timer();

        buttonAnswer1 = object.dialog.findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = object.dialog.findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = object.dialog.findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = object.dialog.findViewById(R.id.buttonAnswer4);

        if (correctMediaPlayer == null) {
            correctMediaPlayer = MediaPlayer.create(object.mapActivity, R.raw.ok);
        }
        if (incorrectMediaPlayer == null) {
            incorrectMediaPlayer = MediaPlayer.create(object.mapActivity, R.raw.error);
        }
        if (triumfMediaPlayer == null) {
            triumfMediaPlayer = MediaPlayer.create(object.mapActivity, R.raw.triumf);
        }

        buttonAnswer1.setOnClickListener(v -> {
            enterAnswer(1);
        });

        buttonAnswer2.setOnClickListener(v -> {
            enterAnswer(2);
        });

        buttonAnswer3.setOnClickListener(v -> {
            enterAnswer(3);
        });

        buttonAnswer4.setOnClickListener(v -> {
            enterAnswer(4);
        });
    }

    private void enterAnswer(int userAnswer) {
        if (pause) return;
        if (trueAnswer == 1) {
            buttonAnswer1.setBackgroundResource(R.drawable.rombgood);
        }
        if (trueAnswer == 2) {
            buttonAnswer2.setBackgroundResource(R.drawable.rombgood);
        }
        if (trueAnswer == 3) {
            buttonAnswer3.setBackgroundResource(R.drawable.rombgood);
        }
        if (trueAnswer == 4) {
            buttonAnswer4.setBackgroundResource(R.drawable.rombgood);
        }

        if (userAnswer == 1 && trueAnswer != 1) {
            buttonAnswer1.setBackgroundResource(R.drawable.rombbad);
        }
        if (userAnswer == 2 && trueAnswer != 2) {
            buttonAnswer2.setBackgroundResource(R.drawable.rombbad);
        }
        if (userAnswer == 4 && trueAnswer != 4) {
            buttonAnswer4.setBackgroundResource(R.drawable.rombbad);
        }
        if (userAnswer == 3 && trueAnswer != 3) {
            buttonAnswer3.setBackgroundResource(R.drawable.rombbad);
        }

        this.userAnswer=userAnswer;
        if (userAnswer != trueAnswer) {
            incorrectMediaPlayer.start();
        } else {
            correctMediaPlayer.start();
        }
        timerDown = new TimerVictorina();
        pause = true;
        timer.schedule(timerDown, 1500, 1500);

        object.beforeEndVictorina(userAnswer == trueAnswer);

    }

    public void loadQuestion(MyMap.Question question) {
        loadQuestion(question.answer1, question.answer2, question.answer3, question.answer4, question.trueAnswer);
    }

    public void loadQuestion(String answer1, String answer2, String answer3, String answer4, int trueAnswer){
        answers = new String[4];
        answers[0]=answer1;
        answers[1]=answer2;
        answers[2]=answer3;
        answers[3]=answer4;
        this.trueAnswer = trueAnswer;
    }


    public void showAnswers() {
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.VISIBLE);
        buttonAnswer1.setText(answers[0]);
        buttonAnswer2.setText(answers[1]);
        buttonAnswer3.setText(answers[2]);
        buttonAnswer4.setText(answers[3]);

        buttonAnswer1.setBackgroundResource(R.drawable.romb);
        buttonAnswer2.setBackgroundResource(R.drawable.romb);
        buttonAnswer3.setBackgroundResource(R.drawable.romb);
        buttonAnswer4.setBackgroundResource(R.drawable.romb);
    }

    class TimerVictorina extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            object.mapActivity.runOnUiThread(() -> {
                // Обновляем фоны всех кнопок
                buttonAnswer1.setBackgroundResource(R.drawable.romb);
                buttonAnswer2.setBackgroundResource(R.drawable.romb);
                buttonAnswer3.setBackgroundResource(R.drawable.romb);
                buttonAnswer4.setBackgroundResource(R.drawable.romb);
                if (timerDown!=null) {
                    timerDown.cancel();
                    timerDown = null;
                }
                pause = false;

                buttonAnswer1.setVisibility(View.VISIBLE);
                buttonAnswer2.setVisibility(View.VISIBLE);
                buttonAnswer3.setVisibility(View.VISIBLE);
                buttonAnswer4.setVisibility(View.VISIBLE);

                object.endVictorina(userAnswer == trueAnswer);

            });
        }
    }

}
