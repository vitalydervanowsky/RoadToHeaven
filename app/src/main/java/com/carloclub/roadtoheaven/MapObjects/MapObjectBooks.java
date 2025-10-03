package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MapObjectBooks extends MapObject {
    private TextView TextMoney;
    int interierID=0;
    TextView[] imageViews;
    private TextView TextFuel;
    String targetWord="ХЛЕБА";
    Date lastSuccess;

    public ArrayList<TextView> moveView;
    public MapObjectBooks(int X, int Y, MapActivity mActivity){
        super(X, Y, mActivity);
        type = "books";
        dialog = new Dialog(mapActivity);
        dialog.setContentView(R.layout.dialog_words);
        imageViews = new TextView[36];

        moveView = new ArrayList<TextView>();

        imageViews[0]=dialog.findViewById(R.id.textLetter1);
        imageViews[1]=dialog.findViewById(R.id.textLetter2);
        imageViews[2]=dialog.findViewById(R.id.textLetter3);
        imageViews[3]=dialog.findViewById(R.id.textLetter4);
        imageViews[4]=dialog.findViewById(R.id.textLetter5);
        imageViews[5]=dialog.findViewById(R.id.textLetter6);
        imageViews[6]=dialog.findViewById(R.id.textLetter7);
        imageViews[7]=dialog.findViewById(R.id.textLetter8);
        imageViews[8]=dialog.findViewById(R.id.textLetter9);
        imageViews[9]=dialog.findViewById(R.id.textLetter10);
        imageViews[10]=dialog.findViewById(R.id.textLetter11);
        imageViews[11]=dialog.findViewById(R.id.textLetter12);
        imageViews[12]=dialog.findViewById(R.id.textLetter13);
        imageViews[13]=dialog.findViewById(R.id.textLetter14);
        imageViews[14]=dialog.findViewById(R.id.textLetter15);
        imageViews[15]=dialog.findViewById(R.id.textLetter16);
        imageViews[16]=dialog.findViewById(R.id.textLetter17);
        imageViews[17]=dialog.findViewById(R.id.textLetter18);
        imageViews[18]=dialog.findViewById(R.id.textLetter19);
        imageViews[19]=dialog.findViewById(R.id.textLetter20);
        imageViews[20]=dialog.findViewById(R.id.textLetter21);
        imageViews[21]=dialog.findViewById(R.id.textLetter22);
        imageViews[22]=dialog.findViewById(R.id.textLetter23);
        imageViews[23]=dialog.findViewById(R.id.textLetter24);
        imageViews[24]=dialog.findViewById(R.id.textLetter25);
        imageViews[25]=dialog.findViewById(R.id.textLetter26);
        imageViews[26]=dialog.findViewById(R.id.textLetter27);
        imageViews[27]=dialog.findViewById(R.id.textLetter28);
        imageViews[28]=dialog.findViewById(R.id.textLetter29);
        imageViews[29]=dialog.findViewById(R.id.textLetter30);
        imageViews[30]=dialog.findViewById(R.id.textLetter31);
        imageViews[31]=dialog.findViewById(R.id.textLetter32);
        imageViews[32]=dialog.findViewById(R.id.textLetter33);
        imageViews[33]=dialog.findViewById(R.id.textLetter34);
        imageViews[34]=dialog.findViewById(R.id.textLetter35);
        imageViews[35]=dialog.findViewById(R.id.textLetter36);


//        for (int i=0; i<36;i++){
//            imageViews[i].setOnTouchListener(new View.OnTouchListener(){
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                return onTouchView(v.getId(),event);
//                }
//            });
//        }
        dialog.findViewById(R.id.layoutLetters).setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchView(v.getId(),event);
            }
        });

        Button buttonStopPuzle = dialog.findViewById(R.id.close);
        buttonStopPuzle.setOnClickListener(v -> {
            dialog.hide();
        });
        //Button buttonStop = dialog.findViewById(R.id.close);
        //buttonStop.setOnClickListener(v -> endFill());

    }
    boolean onTouchView(int ID, MotionEvent event){
        ConstraintLayout v = dialog.findViewById(ID);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //lastY = (int) event.getY();
                //lastX = (int) event.getX();
                //v.setBackground(mapActivity.getDrawable(R.drawable.cell_ok));  //.setImageResource(R.drawable.cell_ok);
                return true;
            case MotionEvent.ACTION_MOVE:
                int clickY = (int) event.getY();
                int clickX = (int) event.getX();
                //определим, какая над ним буква
                if (clickX<305*mapActivity.displayDensity){
                    int vievLetter = (int)((clickY-11*mapActivity.displayDensity)/53/mapActivity.displayDensity)*6 + (int)((clickX-11*mapActivity.displayDensity)/53/mapActivity.displayDensity);
                    if (vievLetter<=0)vievLetter=0;

                    if (vievLetter>=0 && vievLetter<36){
                        imageViews[vievLetter].setBackground(mapActivity.getDrawable(R.drawable.cell_ok));  //.setImageResource(R.drawable.cell_ok);
                        boolean added=false;
                        for (int i=0; i<moveView.size();i++){
                            if (moveView.get(i)==imageViews[vievLetter]) {
                                added = true;
                                break;
                            }
                        }
                        if (!added) moveView.add(imageViews[vievLetter]);
                    }
                }
                return false;
            case MotionEvent.ACTION_UP:
                String moveText="";
                for (int i=0; i<moveView.size();i++){
                    moveText=moveText+moveView.get(i).getText();
                }
                moveView.clear();
                if (moveText.equals(targetWord)) {
                    mapActivity.correctMediaPlayer.start();
                    dialog.hide();
                    lastSuccess = Calendar.getInstance().getTime();
                    Constants.DATAGAME.setStones(Constants.DATAGAME.getStones()+1);
                    DialogMessage.showMessage(R.drawable.gratulation,R.drawable.stones1,"Поздравляем! Вы добыли 1 камень","Собрано: "+String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
                }
                touchFinish();
                return true;
        }
        return false;
    }
    public void touchFinish(){
        for (int i=0;i<36;i++){
            imageViews[i].setBackground(mapActivity.getDrawable(R.drawable.cell));  //ImageDrawable(mActivity.getDrawable(imageSmall)); setImageResource(R.drawable.cell);
        }

    }

    public void EndFill(){
        //mediaPlayer.pause();
        dialog.hide();
    }
    @Override
    public boolean isActual(){
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            return false;
        }
        return true;
    }
    @Override
    public void runAction(){
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            DialogMessage.showMessage(R.drawable.fail,R.drawable.fail,"Технический перерыв: 5 минут","", mapActivity);
            return;
        }

        //Выводім хаотічные числа, и целевое слово среди них
        String alfabet = "АБВГДЕЁЖЗІКЛМНОПРСТУЎФХЦЧШЫьЭЮЯ";
        Random random = new Random();
        for (int i=0; i<36;i++){
            int p = random.nextInt(alfabet.length());
            imageViews[i].setText(alfabet.substring(p,p+1));
        }

        int startX=0;
        int startY=0;
        int stepX=0;
        int stepY=0;
        random = new Random();
        if (random.nextInt(2)==1){
            startY=random.nextInt(6);
            stepX=1;
        }
        else {
            startX=random.nextInt(5);
            stepY=1;}
        MyMap.Words word = (mapActivity.map.wordsForBook[random.nextInt(mapActivity.map.wordsForBook.length-1)]);
        if (type.equals("RM")){
            word = (mapActivity.map.wordsForRM[random.nextInt(mapActivity.map.wordsForRM.length-1)]);
            ((ImageView)dialog.findViewById(R.id.imageDecor)).setImageResource(R.drawable.rm);
        }

        targetWord = word.targetWord;
        ((TextView)dialog.findViewById(R.id.textViewTrials)).setText(word.textBefor+"    ...    "+word.textAfter);
        for (int i=0; i<targetWord.length();i++){
            int p = random.nextInt(alfabet.length());
            imageViews[(startY+i*stepY)*6+startX+i*stepX].setText(targetWord.substring(i,i+1));
        }


        //показываем диалог и растягиваем почти на веь экран
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
        //mediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes){

    }
}
