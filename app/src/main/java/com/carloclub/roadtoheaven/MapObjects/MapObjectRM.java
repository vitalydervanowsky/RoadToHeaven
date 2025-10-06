package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;

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
        dialog.setOnDismissListener(dialog1 -> {
            if (playerAveMaria != null) {
                playerAveMaria.stop();
            }
            if (playerPaterNoster != null) {
                playerPaterNoster.stop();
            }
        });
        dialog.findViewById(R.id.close).setOnClickListener(v -> dialog.dismiss());
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
        if (playerAveMaria.isPlaying()) {
            playerAveMaria.pause();
        }
        if (playerPaterNoster.isPlaying()) {
            playerPaterNoster.pause();
        } else {
            playerPaterNoster.start();
        }
    }

    public void aveMaria() {
        if (playerPaterNoster.isPlaying()) {
            playerPaterNoster.pause();
        }
        if (playerAveMaria.isPlaying()) {
            playerAveMaria.pause();
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
