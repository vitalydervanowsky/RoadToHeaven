package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.helper.TimeUtil;

public class MapObjectRM extends MapObject {
    public MediaPlayer playerPaterNoster;
    public MediaPlayer playerAveMaria;

    public MapObjectRM(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "RM";
        playerPaterNoster = MediaPlayer.create(activity, R.raw.organ);
        playerAveMaria = MediaPlayer.create(activity, R.raw.organ);
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
        //чаще 3 минут не давать
        return !TimeUtil.INSTANCE.isLessThanThreeMinutesPast(lastSuccess);
    }
    @Override
    public void runAction() {
        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
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

            dialog.findViewById(R.id.buttotPaterNoster).setOnClickListener(v -> paterNoster());
            dialog.findViewById(R.id.buttonAveMaria).setOnClickListener(v -> aveMaria());
        }

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
        //ObjectMediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }
}
