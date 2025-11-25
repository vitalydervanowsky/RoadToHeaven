package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.Puzzle;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.helper.MessageUtil;
import com.carloclub.roadtoheaven.helper.TaskUtil;
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
            TaskUtil.INSTANCE.handleTaskSuccess(mapActivity);
            lastSuccess = Calendar.getInstance().getTime();
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
            MessageUtil.INSTANCE.showFailureDialog(Messages.getMessageGalleryTechnicalBreak(), mapActivity);
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
