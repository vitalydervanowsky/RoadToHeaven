package com.carloclub.roadtoheaven.MapObjects;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogActivity;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.Figure;
import com.carloclub.roadtoheaven.FuelView;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.OldQuestions;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Victorina;

import java.util.Timer;
import java.util.TimerTask;

public class MapObjectFuel extends MapObject {
    private TextView textMoney;
    private TextView textFuel;
    FuelView fuelView;

    OldQuestions questions;
    Victorina victorina;
    TextView questionTextView;
    int level=1;

    public MapObjectFuel(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "fuel";
        questions=new OldQuestions();
        mediaPlayer = MediaPlayer.create(activity, R.raw.azs);
    }

    @Override
    public void runAction(){
        dialog.setContentView(R.layout.dialog_fuel);
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        WindowCompat.setDecorFitsSystemWindows(window, false);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );


        Button buttonStop = dialog.findViewById(R.id.endfill);
        buttonStop.setOnClickListener(v -> endFill());

        fuelView = dialog.findViewById(R.id.fuelView);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        fuelView.setImageBitmap(BitmapFactory.decodeResource(mapActivity.getResources(), R.drawable.fuel , options));
        fuelView.invalidate();
        questionTextView = dialog.findViewById(R.id.questionTextView);

        victorina = new Victorina(this, dialog.getWindow().getDecorView());
        showQuestion();

    }

    private void showQuestion() {
        MyMap.Question currentQuestion = questions.getQuestion(level, Constants.LANG_BY);
        questionTextView.setVisibility(View.VISIBLE);
        questionTextView.setText(currentQuestion.question);
        victorina.loadQuestion(currentQuestion.answer1, currentQuestion.answer2, currentQuestion.answer3, currentQuestion.answer4,currentQuestion.trueAnswer);
        victorina.showAnswers();
    }

    public void endFill() {

        dialog.dismiss();
        //mediaPlayer.pause();
    }


    @Override
    public void beforeEndVictorina(boolean isOK) {
        if (isOK){
            mediaPlayer.start();
        }
    }

    @Override
    public void endVictorina(boolean isOK) {
        if (isOK){
            if (level<15)
                level++;
            mediaPlayer.pause();
            Constants.DATAGAME.setFuel(Constants.DATAGAME.getFuel()+4000);
            if (Constants.DATAGAME.getFuel()>=(Constants.DATAGAME.getTank()*1000)){
                Constants.DATAGAME.setFuel(Constants.DATAGAME.getTank()*1000);
                DialogMessage.showMessage(R.drawable.icon_fuel, R.drawable.icon_fuel, "Бак напоўнены. Можна ехаць!", "", mapActivity);
                dialog.dismiss();
            }
            fuelView.invalidate();
        }

        showQuestion();


    }



}
