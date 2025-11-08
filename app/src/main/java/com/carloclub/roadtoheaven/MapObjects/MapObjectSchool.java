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
    public Task task;

    public MapObjectSchool(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "school";
        task = new Task(this);
    }

    private void startFill() {
        //dialog.dismiss();
        dialog.setContentView(R.layout.dialog_class);
    }

    public void endFill() {
        dialog.dismiss();
    }

    @Override
    public boolean isActual(){
        return !task.isStarted;
    }
    @Override
    public void runAction() {
        if (!task.isStarted) {
            Intent intent = new Intent(mapActivity, SchoolActivity.class);
            mapActivity.startActivity(intent);
        }
    }

    @Override
    public void loadAttributes(String[] attributes) {
        //textHello = attributes[3];

    }

    @Override
    public void finishTask() {

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() + 1);


        task.isFinished = true;

        DialogMessage.showMessage(R.drawable.happyboy, R.drawable.icon_ruby, Messages.getMessageYouGetRubyHelp(), "+1", mapActivity);
    }

}
