package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObjectPilgrim extends MapObject {
    public Dialog dialog2;
    public Task task;

    public String textHello;

    public int bonus = 100;

    public MapObjectPilgrim(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "pilgrim";
        task = new Task(this);
        dialog.setContentView(R.layout.dialog_pilgrim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2 = new Dialog(activity);
        dialog2.setContentView(R.layout.dialog_pilgrim2);
        Button buttonStart = dialog.findViewById(R.id.starthelp);
        buttonStart.setOnClickListener(v -> startFill());
        Button buttonClose = dialog.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> endFill());
        Button buttonListen = dialog2.findViewById(R.id.OK);
        buttonListen.setOnClickListener(v -> listen());
        Button buttonClose2 = dialog2.findViewById(R.id.close2);
        buttonClose2.setOnClickListener(v -> noListen());

    }

    public void startFill() {
        dialog.dismiss();
        task.startTask();
        //dialog2.show();
    }

    public void endFill() {
        dialog.dismiss();
    }

    public void listen() {
        dialog2.dismiss();
    }

    public void noListen() {
        dialog2.dismiss();
    }

    @Override
    public void runAction() {
        if (!task.isStarted || task.isFinished) {
            dialog.show();
        }
        //else
        //    dialog2.show();
    }

    @Override
    public void loadAttributes(String[] attributes) {
        textHello = attributes[3];
        int targetX = Integer.valueOf(attributes[1]) - 1;
        int targetY = Integer.valueOf(attributes[2]) - 1;
        task.targetCell = mapActivity.map.mMapCells[targetX][targetY];
    }

    @Override
    public void finishTask() {
        Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() + bonus);

        //dialog3.show();
        task.isFinished = true;


        DialogMessage.showMessage(R.drawable.pilgrim, R.drawable.icon_money, "Спасибо, добрый человек. Вот тебе небольшой подарок от меня: 100 монет", "+" + String.valueOf(bonus), mapActivity);
    }

}
