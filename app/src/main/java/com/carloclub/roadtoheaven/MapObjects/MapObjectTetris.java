package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.Figure;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.PaintView;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

import java.util.Timer;
import java.util.TimerTask;

public class MapObjectTetris extends MapObject {
    public PaintView TetrisView;
    public PaintView NextView;
    TextView ViewSpeed;
    TextView ViewScores;
    TextView ViewBest;
    Figure ActualFigure;
    Figure NextFigure;
    Figure VirtualFigure;
    Timer timer;
    TimerTask mTimerTask;
    TimerDown mtimerDown;
    int GameFigurs=0;
    int OneColor=0;

    int Speed = 0;
    int temp = 700;
    int Scores = 0; //Очки
    int Best = 0;

    boolean GameOver=false;
    //public database DB;

    public MapObjectTetris(int X, int Y, MapActivity mActivity) {
        super(X, Y, mActivity);
        type = "tetris";
        task = new Task(this);



    }

    public void StartFill(){
        dialog.hide();
        mapActivity.myTasks.add(task);
        task.startTask();
    }

    @Override
    public boolean isActual(){
        return !task.isStarted;
    }

    @Override
    public void runAction(){
        ActualFigure = new Figure();
        ActualFigure.GenerateFigure();
        VirtualFigure = new Figure();
        NextFigure = new Figure();
        NextFigure.Clean();
        dialog.setContentView(R.layout.dialog_tetris);
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

        ViewSpeed = (TextView) dialog.findViewById(R.id.textViewSpeed);
        ViewScores = (TextView) dialog.findViewById(R.id.textViewScores);
        ViewBest = (TextView) dialog.findViewById(R.id.textViewBest);
        //ViewBest.setText(Integer.toString(Best));

        TetrisView = (PaintView) dialog.findViewById(R.id.viewDraw);
        TetrisView.invalidate();

        float level = mapActivity.displayDensity; //.getApplicationContext().getResources().getDisplayMetrics().density;
        int H = Math.round(TetrisView.FieldHeight*TetrisView.pixels1Cell);
        int W = Math.round(TetrisView.FieldWidth*TetrisView.pixels1Cell);
        dialog.findViewById(R.id.LL).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,H));
        TetrisView.setLayoutParams(new LinearLayout.LayoutParams(W, LinearLayout.LayoutParams.MATCH_PARENT));
        NextView = (PaintView) dialog.findViewById(R.id.viewNext);
        NextView.FieldWidth = 4;
        NextView.FieldHeight = 4;
        NextView.invalidate();
        W = Math.round(NextView.FieldWidth*NextView.pixels1Cell);
        NextView.setLayoutParams(new LinearLayout.LayoutParams(W,W));
        timer = new Timer();

        ImageButton buttonStart = (ImageButton) dialog.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartGeme();
            }
        });

        ImageButton buttonToLeft = (ImageButton) dialog.findViewById(R.id.imageButtonLeft);
        buttonToLeft.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ActualFigure==null) return false;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {// PRESSED
                        VirtualFigure.CopyFrom(ActualFigure);
                        VirtualFigure.X--;

                        if (TetrisView.ShowPossible(VirtualFigure)) {
                            ActualFigure.CopyFrom(VirtualFigure);
                            TetrisView.invalidate();

                            mtimerDown = new TimerDown();
                            mtimerDown.Orientation=1;
                            timer.schedule(mtimerDown, 200, 60);
                        }
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                        if (mtimerDown!=null) {mtimerDown.cancel(); mtimerDown=null;}
                    case MotionEvent.ACTION_CANCEL:
                        if (mtimerDown!=null) {mtimerDown.cancel(); mtimerDown=null;}
                        break;
                }

                return false;
            }
        });

        ImageButton buttonToDown = (ImageButton) dialog.findViewById(R.id.imageButtonDown);
        buttonToDown.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ActualFigure==null) return false;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {// PRESSED
                        VirtualFigure.CopyFrom(ActualFigure);
                        VirtualFigure.Y = VirtualFigure.Y + 2;

                        if (TetrisView.ShowPossible(VirtualFigure)) {
                            ActualFigure.CopyFrom(VirtualFigure);
                            TetrisView.invalidate();

                            mtimerDown = new TimerDown();
                            timer.schedule(mtimerDown, 100, 60);
                        }
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                        if (mtimerDown!=null) {mtimerDown.cancel(); mtimerDown=null;}
                    case MotionEvent.ACTION_CANCEL:
                        if (mtimerDown!=null) {mtimerDown.cancel(); mtimerDown=null;}
                        break;
                }

                return false;
            }
        });

        ImageButton buttonToRight = (ImageButton) dialog.findViewById(R.id.imageButtonRight);
        buttonToRight.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (ActualFigure==null) return false;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {// PRESSED
                        VirtualFigure.CopyFrom(ActualFigure);
                        VirtualFigure.X++;

                        if (TetrisView.ShowPossible(VirtualFigure)) {
                            ActualFigure.CopyFrom(VirtualFigure);
                            TetrisView.invalidate();

                            mtimerDown = new TimerDown();
                            mtimerDown.Orientation=2;
                            timer.schedule(mtimerDown, 200, 60);
                        }
                    }
                    break;
                    case MotionEvent.ACTION_UP:
                        if (mtimerDown!=null) {mtimerDown.cancel(); mtimerDown=null;}
                    case MotionEvent.ACTION_CANCEL:
                        if (mtimerDown!=null) {mtimerDown.cancel(); mtimerDown=null;}
                        break;
                }

                return false;
            }
        });

        ImageButton buttonTwist = (ImageButton) dialog.findViewById(R.id.imageButtonTwist);
        buttonTwist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActualFigure==null) return;
                VirtualFigure.CopyFrom(ActualFigure);
                VirtualFigure.Twist();
                if (TetrisView.ShowPossible(VirtualFigure)){
                    ActualFigure.CopyFrom(VirtualFigure);
                    TetrisView.invalidate();
                    //mTimerTask.cancel();
                }
            }
        });

        ImageButton buttonPause = (ImageButton) dialog.findViewById(R.id.imageButtonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActualFigure==null) return;
                if (mTimerTask==null) {
                    mTimerTask = new MyTimerTask();
                    timer.schedule(mTimerTask, 100, temp);

                }else {
                    mTimerTask.cancel();
                    mTimerTask=null;
                }

            }
        });
    }

    @Override
    public void loadAttributes(String[] attributes){

    }

    @Override
    public void finishTask(){

            //DialogMessage.showMessage(R.drawable.piligrim,R.drawable.icon_money,"Спасибо, добрый человек. Вот тебе небольшой подарок от меня: 100 монет","+"+String.valueOf(bonus),mapActivity);
    }

    void StartGeme() {
        //TetrisView.ShowFigure(ActualFigure);
        //TetrisView.ActualFigure = ActualFigure;
        //TetrisView.invalidate();
        NextFigure.color = ActualFigure.color;
        TetrisView.ActualFigure = ActualFigure;
        NextFigure.GenerateFigure();
        TetrisView.NextFigure = NextFigure;

        NextView.ActualFigure = new Figure();
        NextView.ActualFigure.CopyFrom(NextFigure);
        NextView.ActualFigure.X=0;
        NextView.ActualFigure.Y=0;
        NextView.invalidate();

        if (GameOver==true){
            GameOver=false;
            Scores=0;
            ViewScores.setText(Integer.toString(Scores));
        }

        if (mTimerTask==null){}
        else {
            mTimerTask.cancel();
            mTimerTask=null;
        }
        mTimerTask = new MyTimerTask();
        timer.schedule(mTimerTask, temp, temp);

    }

    void StoptGeme() {
        TetrisView.cleanALL();
        TetrisView.ActualFigure=null;
        TetrisView.invalidate();
        NextView.ActualFigure = new Figure();
        NextView.ActualFigure.Clean();
        NextView.invalidate();
        Speed = 0;
        temp = 600 - 30 * Speed; //700- Speed*(100-Speed*10);
        ViewSpeed.setText(Integer.toString(Speed));
        mTimerTask.cancel();
        mTimerTask=null;

        if(Scores>Best) {
            Best=Scores;
            ViewBest.setText(Integer.toString(Best));
        }
        GameOver=true;
    }

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            mapActivity.runOnUiThread(new Runnable(){

                // Отображаем информацию в текстовом поле count:
                @Override
                public void run() {
                    if (!TetrisView.ShowPossible(ActualFigure)){
                        //КОНЕЦ ИГРЫ
                        StoptGeme();
                        return;
                    }
                    VirtualFigure.CopyFrom(ActualFigure);
                    VirtualFigure.Y++;
                    if (TetrisView.ShowPossible(VirtualFigure)){
                        ActualFigure.CopyFrom(VirtualFigure);
                        TetrisView.invalidate();
                        //mTimerTask.cancel();
                    }else {
                        int newSpeed = Speed;
                        TetrisView.FixFigure(ActualFigure);
                        int OneColorLines = TetrisView.OneColorLines();
                        newSpeed=newSpeed-OneColorLines;
                        Scores=Scores+(OneColorLines+1)*TetrisView.AnalizFullLines();
                        if (newSpeed<-5){
                            newSpeed=-5;
                            Scores=Scores+1000;
                        }
                        ViewScores.setText(Integer.toString(Scores));


                        ActualFigure.CopyFrom(NextFigure);
                        OneColor++;
                        if (OneColor==5){
                            OneColor=0;
                            NextFigure.GenerateColor();
                        }
                        NextFigure.GenerateFigure();
                        NextView.ActualFigure.CopyFrom(NextFigure);
                        NextView.ActualFigure.X=0;
                        NextView.ActualFigure.Y=0;
                        GameFigurs++;
                        if (GameFigurs>=10) {
                            GameFigurs=0;
                            newSpeed++;
                        }
                        if (newSpeed!=Speed) {
                            Speed=newSpeed;
                            mTimerTask.cancel();
                            temp = 600 - 30 * Speed; //700- Speed*(100-Speed*10);
                            if (temp < 200) return;
                            mTimerTask = new MyTimerTask();
                            timer.schedule(mTimerTask, 100, temp);
                            ViewSpeed.setText(Integer.toString(Speed));
                        }
                        TetrisView.invalidate();
                        NextView.invalidate();

                    }
                }});
        }
    }

    class TimerDown extends TimerTask {
        int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            mapActivity.runOnUiThread(new Runnable(){

                // Отображаем информацию в текстовом поле count:
                @Override
                public void run() {

                    VirtualFigure.CopyFrom(ActualFigure);
                    if (Orientation==0)  VirtualFigure.Y = VirtualFigure.Y + 2;
                    else if (Orientation==1)  VirtualFigure.X--;
                    else VirtualFigure.X++;

                    if (TetrisView.ShowPossible(VirtualFigure)) {
                        ActualFigure.CopyFrom(VirtualFigure);
                        TetrisView.invalidate();
                    }else if (mtimerDown!=null){
                        mtimerDown.cancel();
                        mtimerDown=null;
                    }

                }
            });
        }
    }

}
