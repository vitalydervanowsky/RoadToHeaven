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
    private TextView textMoney;
    private TextView textFuel;

    Fill fill;
    Timer timer;

    public MapObjectFuel(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "fuel";
        timer = new Timer();
        dialog.setContentView(R.layout.dialog_fuel);
        textMoney = dialog.findViewById(R.id.textMoney);
        textFuel = dialog.findViewById(R.id.textFuel);
        Button buttonStart = dialog.findViewById(R.id.startfill);
        buttonStart.setOnClickListener(v -> startFill());
        Button buttonStop = dialog.findViewById(R.id.endfill);
        buttonStop.setOnClickListener(v -> endFill());
        mediaPlayer = MediaPlayer.create(activity, R.raw.azs);
    }

    public void startFill() {
        if (fill != null) {
            fill.cancel();
            fill = null;
        }
        fill = new Fill();
        timer.schedule(fill, 50, 100);
        mediaPlayer.start();
    }

    public void endFill() {
        if (fill != null) {
            fill.cancel();
            fill = null;
        }
        dialog.dismiss();
        mediaPlayer.pause();
    }

    @Override
    public void runAction() {
        textFuel.setText(String.valueOf(Constants.DATAGAME.getFuel() / 1000));
        textMoney.setText(String.valueOf(Constants.DATAGAME.getMoney()));
        dialog.show();
    }

    class Fill extends TimerTask {
        int orientation = 0; // 0 вниз 1- влево  2-вправо

        @Override
        public void run() {
            mapActivity.runOnUiThread(new Runnable() {

                // Отображаем информацию в текстовом поле count:
                @Override
                public void run() {
                    if (Constants.DATAGAME.getFuel() >= (Constants.DATAGAME.getTank() * 1000) || Constants.DATAGAME.getMoney() <= 0) {
                        if (fill != null) {
                            fill.cancel();
                            fill = null;
                        }
                        mediaPlayer.pause();
                        return;
                    }

                    Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() - 5);
                    Constants.DATAGAME.setFuel(Constants.DATAGAME.getFuel() + 1000);

                    textFuel.setText(String.valueOf(Constants.DATAGAME.getFuel() / 1000));
                    textMoney.setText(String.valueOf(Constants.DATAGAME.getMoney()));


                }
            });
        }
    }


}
