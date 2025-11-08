package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;

public class MapObjectSTO extends MapObject {
    private TextView textMoney;

    private TextView textFuel;

    public MapObjectSTO(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "sto";


        updateViews();
    }

    public void payTank() {
        int cost = (int) ((Constants.DATAGAME.getTank() - 20) * 2.5);
        if (Constants.DATAGAME.getMoney() < cost) {
            DialogMessage.showMessage(R.drawable.icon_money, R.drawable.icon_money, Messages.getMessageNotEnoughMoney(), Messages.getMessageGetBalance() + String.valueOf(Constants.DATAGAME.getMoney()), mapActivity);
            return;
        }
        Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() - cost);
        Constants.DATAGAME.setTank(Constants.DATAGAME.getTank() + 20);
        updateViews();
    }

    public void paySpeed() {
        int cost = (int) ((Constants.DATAGAME.getSpeed() - 40) * 2.5);
        if (Constants.DATAGAME.getMoney() < cost) {
            DialogMessage.showMessage(R.drawable.icon_money, R.drawable.icon_money, Messages.getMessageNotEnoughMoney(), Messages.getMessageGetBalance() + String.valueOf(Constants.DATAGAME.getMoney()), mapActivity);
            return;
        }
        Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() - cost);
        Constants.DATAGAME.setSpeed(Constants.DATAGAME.getSpeed() + 20);
        updateViews();
    }

    public void payTire() {
        int cost = (110 - Constants.DATAGAME.getTire()) * 10;
        if (Constants.DATAGAME.getMoney() < cost) {
            DialogMessage.showMessage(R.drawable.icon_money, R.drawable.icon_money, Messages.getMessageNotEnoughMoney(), Messages.getMessageGetBalance() + String.valueOf(Constants.DATAGAME.getMoney()), mapActivity);
            return;
        }
        Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() - cost);
        Constants.DATAGAME.setTire(Constants.DATAGAME.getTire() - 10);
        updateViews();
    }

    public void updateViews() {
        ((TextView) dialog.findViewById(R.id.textTank)).setText(String.valueOf(Constants.DATAGAME.getTank()));
        if (Constants.DATAGAME.getTank() >= 100) {
            dialog.findViewById(R.id.textTankNew).setVisibility(View.INVISIBLE);
            dialog.findViewById(R.id.payTank).setVisibility(View.INVISIBLE);
        } else {
            dialog.findViewById(R.id.textTankNew).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.payTank).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.textTankNew)).setText("-->" + String.valueOf(Constants.DATAGAME.getTank() + 20));
            ((Button) dialog.findViewById(R.id.payTank)).setText(String.valueOf((Constants.DATAGAME.getTank() - 20) * 2.5)); //Первое улучшение 50, каждое последующее х2
        }

        ((TextView) dialog.findViewById(R.id.textSpeed)).setText(String.valueOf(Constants.DATAGAME.getSpeed()));
        if (Constants.DATAGAME.getSpeed() >= 120) {
            dialog.findViewById(R.id.textSpeedNew).setVisibility(View.INVISIBLE);
            dialog.findViewById(R.id.paySpeed).setVisibility(View.INVISIBLE);
        } else {
            dialog.findViewById(R.id.textSpeedNew).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.paySpeed).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.textSpeedNew)).setText("-->" + String.valueOf(Constants.DATAGAME.getSpeed() + 20));
            ((Button) dialog.findViewById(R.id.paySpeed)).setText(String.valueOf((Constants.DATAGAME.getSpeed() - 40) * 2.5)); //Первое улучшение 50, каждое последующее х2
        }

        ((TextView) dialog.findViewById(R.id.textTire)).setText(String.valueOf(Constants.DATAGAME.getTire()));
        if (Constants.DATAGAME.getTire() < 80) {
            dialog.findViewById(R.id.textTireNew).setVisibility(View.INVISIBLE);
            dialog.findViewById(R.id.PayTire).setVisibility(View.INVISIBLE);
        } else {
            dialog.findViewById(R.id.textTireNew).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.PayTire).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.textTireNew)).setText("-->" + String.valueOf(Constants.DATAGAME.getTire() - 10));
            ((Button) dialog.findViewById(R.id.PayTire)).setText(String.valueOf((110 - Constants.DATAGAME.getTire()) * 10)); //Первое улучшение 100, каждое последующее х2
        }
    }

    public void endFill() {
        //ObjectMediaPlayer.pause();
        dialog.dismiss();
    }

    @Override
    public void runAction() {
        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_sto);
            Button buttonStop = dialog.findViewById(R.id.close);
            buttonStop.setOnClickListener(v -> endFill());
            dialog.findViewById(R.id.payTank).setOnClickListener(v -> payTank());
            dialog.findViewById(R.id.paySpeed).setOnClickListener(v -> paySpeed());
            dialog.findViewById(R.id.PayTire).setOnClickListener(v -> payTire());
        }

        updateViews();
        dialog.show();
        //ObjectMediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }
}
