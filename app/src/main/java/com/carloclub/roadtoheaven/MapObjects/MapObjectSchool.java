package com.carloclub.roadtoheaven.MapObjects;

import android.content.Intent;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;
import com.carloclub.roadtoheaven.school.SchoolActivity;

public class MapObjectSchool extends MapObject {

    public Task taskB;

    public MapObjectSchool(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "school";
        task = new Task(this);
        taskB = new Task(this);
    }

    @Override
    public boolean isActual() {
        return !task.isStarted;
    }

    @Override
    public void runAction() {
        Intent intent = new Intent(mapActivity, SchoolActivity.class);
        intent.putExtra(Constants.CITY_ARG, mapActivity.city);
        mapActivity.startActivityForResult(intent, 111);
    }

    @Override
    public void endVictorina(boolean isOK) {
        super.endVictorina(isOK);
        if (isOK) {
            if (task.isStarted) {
                task.isFinished = true;
            } else {
                taskB.isFinished = true;
            }
            Constants.DATAGAME.setStones(Constants.DATAGAME.getStones() + 1);
            mapActivity.updateBar();

            if (Constants.DATAGAME.getStones() == 7) {
                DialogMessage.showMessage(R.drawable.bridge, R.drawable.stones1, Messages.getMessageGotAllStones(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            } else {
                DialogMessage.showMessage(R.drawable.gratulation, R.drawable.stones1, Messages.getMessageGotStone(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            }
            mapActivity.showRubies();
        }
    }
}
