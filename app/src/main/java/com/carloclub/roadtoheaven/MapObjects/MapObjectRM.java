package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;
import android.widget.Button;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

import java.util.Calendar;
import java.util.Date;

public class MapObjectRM extends MapObject {
    public MediaPlayer playerPaterNoster;
    public MediaPlayer playerAveMaria;
    Date lastSuccess;

    public MapObjectRM(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "RM";
        dialog.setContentView(R.layout.dialog_rm);
        Button buttonStop = dialog.findViewById(R.id.close);
        buttonStop.setOnClickListener(v -> endFill());
        playerPaterNoster = MediaPlayer.create(activity, R.raw.organ);
        playerAveMaria = MediaPlayer.create(activity, R.raw.organ);
        dialog.findViewById(R.id.buttotPaterNoster).setOnClickListener(v -> paterNoster());
        dialog.findViewById(R.id.buttonAveMaria).setOnClickListener(v -> aveMaria());
    }

    public void endFill() {
        playerPaterNoster.stop();
        playerAveMaria.stop();
        dialog.dismiss();
    }

    public void paterNoster() {
        if (playerPaterNoster.isPlaying()) {
            playerPaterNoster.stop();
        } else {
            playerPaterNoster.start();
        }
    }

    public void aveMaria() {
        if (playerAveMaria.isPlaying()) {
            playerAveMaria.stop();
        } else {
            playerAveMaria.start();
        }
    }

    @Override
    public boolean isActual(){
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            return false;
        }
        return true;
    }
    @Override
    public void runAction() {
        dialog.show();
        //ObjectMediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }
}
