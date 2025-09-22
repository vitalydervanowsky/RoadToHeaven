package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.TextView;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

import java.util.Timer;
import java.util.TimerTask;

public class MapObjectFuel extends MapObject {
    private TextView TextMoney;


    Fill fill;
    Timer timer;

    private TextView TextFuel;
    public MapObjectFuel(int X, int Y, MapActivity mActivity){
        super(X, Y, mActivity);
        type = "fuel";
        timer = new Timer();
        dialog.setContentView(R.layout.dialog_fuel);
        TextMoney = dialog.findViewById(R.id.textMoney);
        TextFuel = dialog.findViewById(R.id.textFuel);
        Button buttonStart = dialog.findViewById(R.id.startfill);
        buttonStart.setOnClickListener(v -> StartFill());
        Button buttonStop = dialog.findViewById(R.id.endfill);
        buttonStop.setOnClickListener(v -> EndFill());
        ObjectMediaPlayer = MediaPlayer.create(mActivity, R.raw.azs);
    }

    public void StartFill(){
        if (fill!=null) {fill.cancel(); fill=null;}
        fill = new Fill();
        timer.schedule(fill, 50, 100);
        ObjectMediaPlayer.start();
    }

    public void EndFill(){
        if (fill!=null) {fill.cancel(); fill=null;}
        dialog.hide();
        ObjectMediaPlayer.pause();
    }

    @Override
    public void RunAction(){
        TextFuel.setText(String.valueOf(Constants.DATAGAME.getFuel() /1000));
        TextMoney.setText(String.valueOf(Constants.DATAGAME.getMoney()));
        dialog.show();
    }

    class Fill extends TimerTask {
        int Orientation =0; // 0 вниз 1- влево  2-вправо
        @Override
        public void run() {
            mapActivity.runOnUiThread(new Runnable(){

                // Отображаем информацию в текстовом поле count:
                @Override
                public void run() {
                    if (Constants.DATAGAME.getFuel()  >= (Constants.DATAGAME.getTank()*1000) || Constants.DATAGAME.getMoney() <=0){
                        if (fill!=null) {fill.cancel(); fill=null;}
                        ObjectMediaPlayer.pause();
                        return;
                    }

                    Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() -5);
                    Constants.DATAGAME.setFuel(Constants.DATAGAME.getFuel() +1000);

                    TextFuel.setText(String.valueOf(Constants.DATAGAME.getFuel() /1000));
                    TextMoney.setText(String.valueOf(Constants.DATAGAME.getMoney()));


                }
            });
        }
    }


}
