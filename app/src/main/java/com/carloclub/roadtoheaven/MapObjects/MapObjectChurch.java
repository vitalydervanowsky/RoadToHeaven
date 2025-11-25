package com.carloclub.roadtoheaven.MapObjects;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Task;
import com.carloclub.roadtoheaven.helper.TaskUtil;
import com.carloclub.roadtoheaven.story.StoryHelper;

public class MapObjectChurch extends MapObject {

    public MapObjectChurch(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "church";
        task = new Task(this);
    }

    @Override
    public void runAction() {
        StoryHelper.INSTANCE.showStoryActivityForResult(mapActivity, mapActivity.city);
    }

    @Override
    public void endVictorina(boolean isOK) {
        super.endVictorina(isOK);
        if (isOK) {
            task.isFinished = true;
            TaskUtil.INSTANCE.handleTaskSuccess(mapActivity);
        }
    }
}
