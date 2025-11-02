package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.media.MediaPlayer;

import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObject {
    public int x;
    public int y;
    public String type;
    public Dialog dialog;
    public MapActivity mapActivity;
    public Task task;
    public MediaPlayer mediaPlayer;

    public MapObject(int x, int y, MapActivity activity) {
        this.x = x;
        this.y = y;
        mapActivity = activity;
        dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
        dialog.setOnDismissListener(dialog -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });
    }


    public void runAction() {
        DialogMessage.showMessage(0,0,"Привет! У меня пока нет для тебя заданий","", mapActivity);
    }
    public void finishTask() {

    }
    public void updateData() {

    }

    public void endVictorina(boolean isOK){

    }

    public void beforeEndVictorina(boolean isOK){

    }

    public void loadAttributes(String[] attributes) {

    }

    public boolean isActual(){
        return true;
    }


}
