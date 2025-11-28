package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObjectHunger extends MapObject {

    public MapObjectHunger(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "hunger";
        task = new Task(this);

    }

    private void startFill() {
        if (Constants.DATAGAME.getBurgers() < 1) {
            DialogMessage.showMessage(R.drawable.fail, R.drawable.burger, Messages.getMessageYouHaveNoBurgers(), "0", mapActivity);
            return;
        }

        Constants.DATAGAME.setBurgers(Constants.DATAGAME.getBurgers() - 1);

        dialog.dismiss();
        task.startTask();
        task.finishTask();
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
        if (!isActual()) return;

        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_hunger);

            Button buttonStart = dialog.findViewById(R.id.starthelp);
            buttonStart.setOnClickListener(v -> startFill());

            Button buttonClose = dialog.findViewById(R.id.close);
            buttonClose.setOnClickListener(v -> endFill());
        }
        dialog.show();
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }

    @Override
    public void finishTask() {

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() + 1);


        task.isFinished = true;

        DialogMessage.showMessage(0, R.drawable.icon_ruby, Messages.getMessageYouGetRubyHelp(), "+1", mapActivity);
    }

}
