package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.Puzzle;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.helper.TimeUtil;

import java.util.Calendar;
import java.util.Date;

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
        if (!isActual()) { //чаще 3 минут не давать
            DialogMessage.showMessage(R.drawable.fail, R.drawable.fail, Messages.getMessageGalleryTechnicalBreak(), "", mapActivity);
            return;
        }

        showPuzzle();
    }

    @Override
    public boolean isActual(){
        //чаще 3 минут не давать
        return !TimeUtil.INSTANCE.isLessThanThreeMinutesPast(lastSuccess);
    }
    public void showPuzzle() {
        puzzle=new Puzzle(this, mapActivity.map.cinemaQuestion.get(0));
        puzzle.startPuzzle();

    }

    @Override
    public void loadAttributes(String[] attributes) {

    }


}
