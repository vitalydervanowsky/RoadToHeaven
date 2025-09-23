package com.carloclub.roadtoheaven;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BridgeActivity extends AppCompatActivity {
    TextView buttonAnswer1;
    TextView buttonAnswer2;
    TextView buttonAnswer3;
    TextView buttonAnswer4;
    TextView buttonAdvice;
    TextView buttonRules;
    TextView buttonLang;
    TextView questionTextView;
    int step = 0;
    boolean pause = false;
    boolean isGameOver = true;
    String lang = Constants.LANG_BY;
    Dialog dialog;
    Dialog dialogAI;
    Button buttonThank;
    private MediaPlayer correctMediaPlayer;
    private MediaPlayer incorrectMediaPlayer;
    private MediaPlayer triumfMediaPlayer;
    Timer timer;
    TimerDown timerDown;

    MyMap.Question[] questions;

    int maxStep = 7;
    
    ArrayList <ImageView> imageStones;
    boolean[] usedRubies = new boolean[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);
        //sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
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

        questions = Constants.DATAGAME.map.getQuestions(maxStep, Constants.LANG_RU);

        questionTextView = findViewById(R.id.questionTextView);
        //stepTextView = findViewById(R.id.stepTextView);

        //imageView = findViewById(R.id.imageView);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonAdvice = findViewById(R.id.buttonAdvice);
        buttonRules = findViewById(R.id.buttonRules);
        buttonLang = findViewById(R.id.buttonLang);
        imageStones = new ArrayList<ImageView>();
        imageStones.add(findViewById(R.id.imageStone1));
        imageStones.add(findViewById(R.id.imageStone2));
        imageStones.add(findViewById(R.id.imageStone3));
        imageStones.add(findViewById(R.id.imageStone4));
        imageStones.add(findViewById(R.id.imageStone5));
        imageStones.add(findViewById(R.id.imageStone6));
        imageStones.add(findViewById(R.id.imageStone7));

        initButtons();
        initMediaPlayers();
        startGame();
        timer = new Timer();

    }

    private void showStartMenu() {
        buttonAnswer1.setBackgroundResource(R.drawable.romb);
        buttonAnswer2.setBackgroundResource(R.drawable.rombgood);
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.INVISIBLE);
        buttonAnswer3.setVisibility(View.INVISIBLE);
        buttonAnswer4.setVisibility(View.INVISIBLE);
        if (Constants.DATAGAME.getRubies()>0) {
            buttonAnswer2.setVisibility(View.VISIBLE);
            findViewById(R.id.imageViewRubi).setVisibility(View.VISIBLE);
        }
        questionTextView.setText("К сожалению, Вы ошиблись(((\n  Cтоительство моста прервано");
        buttonAnswer1.setText("Завершить стройку");
        buttonAnswer2.setText("1 Рубин Помощи");

        ImageView imageStone=imageStones.get(step-1);
        imageStone.setImageDrawable(getDrawable(R.drawable.badstone));
    }

    private void initButtons() {
        buttonAnswer1.setOnClickListener(v -> {
//            if (isGameOver) {
//                startGame();
//            } else {
                enterAnswer(1);
            //}
        });

        buttonAnswer2.setOnClickListener(v -> {
//            if (isGameOver) {
//                showRules();
//            } else {
                enterAnswer(2);
            //}
        });

        buttonAnswer3.setOnClickListener(v -> {
            if (isGameOver) {
                writeReview();
            } else {
                enterAnswer(3);
            }
        });
        buttonAnswer4.setOnClickListener(v -> {
            if (isGameOver) {
                finish();
            } else {
                enterAnswer(4);
            }
        });



       // buttonThank.setOnClickListener(v -> dialog.hide());

        buttonLang.setOnClickListener(v -> changeLang());

    }

    private void changeDiff() {

    }

    private void changeLang() {
        if (isBy()) {
            lang = Constants.LANG_RU;
        } else {
            lang = Constants.LANG_BY;
        }
        buttonLang.setText(lang);
        if (isGameOver) {
            showStartMenu();
        } else{
            int v1 = buttonAnswer1.getVisibility();
            int v2 = buttonAnswer2.getVisibility();
            int v3 = buttonAnswer3.getVisibility();
            int v4 = buttonAnswer4.getVisibility();
            showQuestion();
            buttonAnswer1.setVisibility(v1);
            buttonAnswer2.setVisibility(v2);
            buttonAnswer3.setVisibility(v3);
            buttonAnswer4.setVisibility(v4);
        }
    }

    private void writeReview() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void startGame() {
        //findViewById(R.id.MainLayout).setBackground(ContextCompat.getDrawable(this, R.drawable.heaven));

        step = 1;
        isGameOver = false;
        showQuestion();
    }


    private void showParams() {

    }

    private void showRules() {

    }

    private void showAdviceAI(String text) {

    }

    private void showAdvice(String text) {
    }
    private void updateStones(int stoneAnimate) {
        for (int i=1; i<=7;i++){
            ImageView imageStone=imageStones.get(i-1);
            if (i==stoneAnimate) {
                imageStone.setImageDrawable(getDrawable(R.drawable.stoneanimation));
                AnimationDrawable isAnimation = (AnimationDrawable) imageStone.getDrawable();
                isAnimation.start();
            } else if (usedRubies[i-1]==true) {
                imageStone.setImageDrawable(getDrawable(R.drawable.rubistone));
            } else if (i<step) {
                imageStone.setImageDrawable(getDrawable(R.drawable.stone));
            }
            else imageStone.setImageDrawable(getDrawable(R.drawable.nostone));

        }
        
    }

    private void enterAnswer(int userAnswer) {
        if (pause || isGameOver) {
            if (userAnswer==2){
                    Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies()-1);
                    findViewById(R.id.imageViewRubi).setVisibility(View.INVISIBLE);
                    usedRubies[step-1]=true;
                    step++;
                    isGameOver=false;
                    updateStones(0);
                    showQuestion();

            }
            else finish();

            return;

        };
        Constants.DATAGAME.setStones(Constants.DATAGAME.getStones()-1);

        int trueAnswer = questions[step-1].trueAnswer;
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
        isGameOver = userAnswer != trueAnswer;
        timerDown = new TimerDown();
        pause = true;
        timer.schedule(timerDown, 1500, 1500);
        if (isGameOver) {
            incorrectMediaPlayer.start();
            // выведем диалог и завершим активность
        } else {
            updateStones(step);
            correctMediaPlayer.start();
        }
    }

    private void showQuestion() {
        MyMap.Question currentQuestion = questions[step - 1];
        questionTextView.setVisibility(View.VISIBLE);
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.VISIBLE);
        questionTextView.setText(currentQuestion.question);
        buttonAnswer1.setText(currentQuestion.answer1);
        buttonAnswer2.setText(currentQuestion.answer2);
        buttonAnswer3.setText(currentQuestion.answer3);
        buttonAnswer4.setText(currentQuestion.answer4);
        buttonAnswer2.setBackgroundResource(R.drawable.romb);
        //stepTextView.setText(getString(R.string.step_label, step, Constants.QUIZ_SIZE_HARD));
    }

    private void initMediaPlayers() {
        if (correctMediaPlayer == null) {
            correctMediaPlayer = MediaPlayer.create(this, R.raw.ok);
        }
        if (incorrectMediaPlayer == null) {
            incorrectMediaPlayer = MediaPlayer.create(this, R.raw.error);
        }
        if (triumfMediaPlayer == null) {
            triumfMediaPlayer = MediaPlayer.create(this, R.raw.triumf);
        }
    }

    private boolean isBy() {
        return lang.equals(Constants.LANG_BY);
    }

    class TimerDown extends TimerTask {
        //int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            runOnUiThread(() -> {
                // Обновляем фоны всех кнопок
                buttonAnswer1.setBackgroundResource(R.drawable.romb);
                buttonAnswer2.setBackgroundResource(R.drawable.romb);
                buttonAnswer3.setBackgroundResource(R.drawable.romb);
                buttonAnswer4.setBackgroundResource(R.drawable.romb);
                timerDown.cancel();
                timerDown = null;
                pause = false;

                buttonAnswer1.setVisibility(View.VISIBLE);
                buttonAnswer2.setVisibility(View.VISIBLE);
                buttonAnswer3.setVisibility(View.VISIBLE);
                buttonAnswer4.setVisibility(View.VISIBLE);

                if (isGameOver) {
                    //step = 0;
                    showStartMenu();
                } else if (step == maxStep) { //
                    isGameOver = true;
                    step++;
                    updateStones(0);
                    triumfMediaPlayer.start();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    //moveImageView(step);
                    step++;
                    updateStones(0);
                    showQuestion();
                }
            });
        }
    }

}
