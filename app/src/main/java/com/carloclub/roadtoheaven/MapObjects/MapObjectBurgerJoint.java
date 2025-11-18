package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObjectBurgerJoint extends MapObject {
    public Task task;

    public MapObjectBurgerJoint(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "BurgerJoint";
        task = new Task(this);
        //dialog.setContentView(R.layout.dialog_burger);

    }

    private void pay() {
        int m = Constants.DATAGAME.getMoney();
        if (m < 5) {
            DialogMessage.showMessage(R.drawable.fail, R.drawable.icon_money, Messages.getMessageNotEnoughMoneyForBurger(), String.valueOf(m) + "p.", mapActivity);
            return;
        }
        Constants.DATAGAME.setMoney(m - 5);

        Constants.DATAGAME.setBurgers(Constants.DATAGAME.getBurgers() + 1);
        DialogMessage.showMessage(R.drawable.hunger, R.drawable.icon_burger, Messages.getMessageYouHaveSnack(), "+1", mapActivity);
        dialog.dismiss();
    }

    public void endFill() {
        dialog.dismiss();
    }

    @Override
    public void runAction() {
        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_burger);

            Button buttonStart = dialog.findViewById(R.id.buttonPay);
            buttonStart.setOnClickListener(v -> pay());

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

        DialogMessage.showMessage(R.drawable.happyboy, R.drawable.icon_ruby, Messages.getMessageYouGetRubyHelp(), "+1", mapActivity);
    }

}
