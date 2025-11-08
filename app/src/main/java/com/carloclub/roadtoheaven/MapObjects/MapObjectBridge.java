package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.BridgeActivity;
import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogActivity;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Victorina;

import java.util.ArrayList;

public class MapObjectBridge extends MapObject {
    TextView buttonAnswer1;
    TextView buttonAnswer2;
    TextView buttonAnswer3;
    TextView buttonAnswer4;
    TextView questionTextView;
    boolean isGameOver = true;
    private MediaPlayer triumfMediaPlayer;
    MyMap.Question[] questions;
    int step = 0;
    int maxStep = 7;

    Victorina victorina;

    ArrayList <ImageView> imageStones;
    boolean[] usedRubies = new boolean[7];

    public MapObjectBridge(int X, int Y, MapActivity activity){
        super(X, Y, activity);
        type = "bridge";

    }


    @Override
    public void runAction(){

        if ((Constants.DATAGAME.getStones()+Constants.DATAGAME.getRubies())<7) {
            DialogMessage.showMessage(R.drawable.icon_stones, R.drawable.icon_stones, Messages.getMessageNotEnoughStones(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            return;
        }
//        //запускаем строительство моста
//        Intent i = new Intent(mapActivity, BridgeActivity.class);
//        mapActivity.startActivityForResult(i,0);

        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
        }
        dialog.setContentView(R.layout.activity_bridge);
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

        questions = mapActivity.map.getQuestions(maxStep, "Ru");

        questionTextView = dialog.findViewById(R.id.questionTextView);
        //stepTextView = findViewById(R.id.stepTextView);

        //imageView = findViewById(R.id.imageView);
        buttonAnswer1 = dialog.findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = dialog.findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = dialog.findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = dialog.findViewById(R.id.buttonAnswer4);
        imageStones = new ArrayList<ImageView>();
        imageStones.add(dialog.findViewById(R.id.imageStone1));
        imageStones.add(dialog.findViewById(R.id.imageStone2));
        imageStones.add(dialog.findViewById(R.id.imageStone3));
        imageStones.add(dialog.findViewById(R.id.imageStone4));
        imageStones.add(dialog.findViewById(R.id.imageStone5));
        imageStones.add(dialog.findViewById(R.id.imageStone6));
        imageStones.add(dialog.findViewById(R.id.imageStone7));

        if (triumfMediaPlayer == null) {
            triumfMediaPlayer = MediaPlayer.create(mapActivity, R.raw.triumf);
        }
        step = 1;
        isGameOver = false;
        victorina = new Victorina(this, dialog.getWindow().getDecorView());
        showQuestion();
    }

    private void showQuestion() {
        MyMap.Question currentQuestion = questions[step - 1];
        questionTextView.setVisibility(View.VISIBLE);
        questionTextView.setText(currentQuestion.question);
        victorina.loadQuestion(currentQuestion.answer1, currentQuestion.answer2, currentQuestion.answer3, currentQuestion.answer4,currentQuestion.trueAnswer);
        victorina.showAnswers();
    }

    private void updateStones(int stoneAnimate) {
        for (int i=1; i<=7;i++){
            ImageView imageStone=imageStones.get(i-1);
            if (i==stoneAnimate) {
                imageStone.setImageDrawable(mapActivity.getDrawable(R.drawable.stoneanimation));
                AnimationDrawable isAnimation = (AnimationDrawable) imageStone.getDrawable();
                isAnimation.start();
            } else if (usedRubies[i-1]==true) {
                imageStone.setImageDrawable(mapActivity.getDrawable(R.drawable.rubistone));
            } else if (i<step) {
                imageStone.setImageDrawable(mapActivity.getDrawable(R.drawable.stone));
            }
            else imageStone.setImageDrawable(mapActivity.getDrawable(R.drawable.nostone));

        }

    }

    @Override
    public void beforeEndVictorina(boolean isOK) {
        if (!isOK){
            if(Constants.DATAGAME.getRubies()>0){
                Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies()-1);
                usedRubies[step-1]=true;
                //DialogMessage.showMessage(R.drawable.fail, R.drawable.icon_ruby, "Нажаль, ты памыліўся, але рубін дапамогі цябе ўратаваў", "", mapActivity);
            }
            else {
                isGameOver=true;
                //dialog.dismiss();
                //return;
            }
        }
        else if (Constants.DATAGAME.getStones()==0 && Constants.DATAGAME.getRubies()>0){
            Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies()-1);
            usedRubies[step-1]=true;
        }
        else
            Constants.DATAGAME.setStones(Constants.DATAGAME.getStones()-1);

        updateStones(step);

    }

    @Override
    public void endVictorina(boolean isOK) {
        if (!isOK){
            if(isGameOver){
                DialogMessage.showMessage(R.drawable.fail, R.drawable.icon_ruby, Messages.getMessageMistake(), "", mapActivity);
                dialog.dismiss();
                return;
            }
            else {
                DialogMessage.showMessage(R.drawable.fail, R.drawable.icon_ruby, Messages.getMessageMistakeWithRuby(), "", mapActivity);
            }
        }


        if (step == maxStep) { //
            isGameOver = true;
            step++;
            updateStones(0);
            triumfMediaPlayer.start();

            Intent i = new Intent(mapActivity, DialogActivity.class);
            i.putExtra("videoPath", "android.resource://" + mapActivity.getPackageName() + "/" + R.raw.complete);
            //i.putExtra("CityName", "Sokolka");
            mapActivity.startActivityForResult(i, 0);

            dialog.dismiss();
            mapActivity.finish();
        } else {
            //moveImageView(step);
            step++;
            updateStones(0);
            showQuestion();
        }


    }

}
