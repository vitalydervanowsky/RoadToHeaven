package com.carloclub.roadtoheaven.MapObjects;

import android.content.Intent;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Task;
import com.carloclub.roadtoheaven.helper.TaskUtil;
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
            TaskUtil.INSTANCE.handleTaskSuccess(mapActivity);
        }
    }
}
