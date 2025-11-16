package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObjectPilgrim extends MapObject {
    public Dialog dialog2;

    public String textHello;

    public int bonus=100;

    public MapObjectPilgrim(int X, int Y, MapActivity mActivity) {
        super(X, Y, mActivity);
        type = "pilgrim";
        task = new Task(this);
    }

    public void StartFill(){
        dialog.hide();
        mapActivity.myTasks.add(task);
        task.startTask();
    }

    @Override
    public boolean isActual(){
        return !task.isStarted && !task.isFinished;
    }

    @Override
    public void runAction(){
        if (!task.isStarted) {
            DialogMessage.showMessage(0, 0, Messages.getMessageFromPilgrim(), Messages.getMessageThanks(), mapActivity, R.drawable.karalina);
            mapActivity.myTasks.add(task);
            task.startTask();
            mapActivity.showRubies();
        }
    }

    @Override
    public void loadAttributes(String[] attributes){
        textHello = attributes[3];
        int targetX = Integer.valueOf(attributes[1])-1;
        int targetY = Integer.valueOf(attributes[2])-1;
        task.targetCell = mapActivity.map.mMapCells[targetX][targetY];
    }

    @Override
    public void finishTask(){
        Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() +bonus);

        //dialog3.show();
        task.isFinished = true;

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() +1);
        DialogMessage.showMessage(R.drawable.happyboy,R.drawable.icon_ruby,"За Твоё доброе сердце Ты полчаешь 1 Рубин Помощи","+1",mapActivity);
        //DialogMessage.showMessage(R.drawable.piligrim,R.drawable.icon_money,"Спасибо, добрый человек. Вот тебе небольшой подарок от меня: 100 монет","+"+String.valueOf(bonus),mapActivity);
    }

}
