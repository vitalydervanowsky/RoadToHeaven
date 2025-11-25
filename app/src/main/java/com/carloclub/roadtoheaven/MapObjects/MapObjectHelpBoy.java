package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObjectHelpBoy extends MapObject {
    public Task task;

    public MapObjectHelpBoy(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "helpboy";
        task = new Task(this);

    }

    private void startFill() {
        dialog.dismiss();
        mapActivity.myTasks.add(task);
        task.startTask();

        mapActivity.showRubies();
    }

    public void endFill() {
        dialog.dismiss();
    }

    @Override
    public boolean isActual(){
        return !task.isStarted && !task.isFinished;
    }
    @Override
    public void runAction() {
        if(!isActual()) return;
        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_helpboy);

            Button buttonStart = dialog.findViewById(R.id.starthelp);
            buttonStart.setOnClickListener(v -> startFill());

            Button buttonClose = dialog.findViewById(R.id.close);
            buttonClose.setOnClickListener(v -> endFill());
        }
        if (!task.isStarted)
            dialog.show();
    }

    @Override
    public void loadAttributes(String[] attributes) {
        //textHello = attributes[3];
        //int targetX = Integer.valueOf(attributes[1]) - 1;
        //int targetY = Integer.valueOf(attributes[2]) - 1;
        //task.targetCell = mapActivity.map.mMapCells[targetX][targetY];
        task.targetType = "hospital_";
    }

    @Override
    public void finishTask() {

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() + 1);


        task.isFinished = true;

        DialogMessage.showMessage(R.drawable.happyboy, R.drawable.icon_ruby, Messages.getMessageYouGetRubyHelp(), "+1", mapActivity);
    }

}
