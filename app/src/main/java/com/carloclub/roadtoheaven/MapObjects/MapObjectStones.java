package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.BridgeActivity;
import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.CustomImageView;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.Puzzle;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Victorina;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MapObjectStones extends MapObject {
    Puzzle puzzle;
    Date lastSuccess;
    private MediaPlayer triumfMediaPlayer;

    public MapObjectStones(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "stones";

        if (triumfMediaPlayer == null) {
            triumfMediaPlayer = MediaPlayer.create(mapActivity, R.raw.triumf);
        }

    }

    @Override
    public void endVictorina(boolean isOK) {
        if (!isOK) {

        } else {
            triumfMediaPlayer.start();
            Constants.DATAGAME.setStones(Constants.DATAGAME.getStones() + 1);
            mapActivity.updateBar();

            if (Constants.DATAGAME.getStones() == 7) {
                DialogMessage.showMessage(R.drawable.bridge, R.drawable.stones1, Messages.getMessageGotAllStones(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            } else {
                DialogMessage.showMessage(R.drawable.gratulation, R.drawable.stones1, Messages.getMessageGotStone(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            }
            lastSuccess = Calendar.getInstance().getTime();
            mapActivity.showRubies();
        }
        puzzle.dialog.hide();

    }

    public void endFill() {
        mediaPlayer.pause();
        dialog.dismiss();
    }

    @Override
    public void runAction() {
        if (lastSuccess != null && (Calendar.getInstance().getTime().getTime() - lastSuccess.getTime()) < 180000) { //чаще 3 минут не давать
            DialogMessage.showMessage(R.drawable.fail, R.drawable.fail, Messages.getMessageGalleryTechnicalBreak(), "", mapActivity);
            return;
        }

        showPuzzle();
    }

    @Override
    public boolean isActual(){
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            return false;
        }
        return true;
    }
    public void showPuzzle() {
        puzzle=new Puzzle(this, mapActivity.map.cinemaQuestion.get(0));
        puzzle.startPuzzle();

    }

    @Override
    public void loadAttributes(String[] attributes) {

    }


}
