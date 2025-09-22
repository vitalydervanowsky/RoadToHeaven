package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Tasks;

public class MapObjectPiligrim extends MapObject {
    public Dialog dialog2;
    public Tasks task;

    public String textHello;

    public int bonus=100;

    public MapObjectPiligrim(int X, int Y, MapActivity mActivity){
        super(X, Y, mActivity);
        type = "piliggrim";
        task = new Tasks((MapObject)this);
        dialog.setContentView(R.layout.dialog_piligrim);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2 = new Dialog(mActivity);
        dialog2.setContentView(R.layout.dialog_piligrim2);
        Button buttonStart = dialog.findViewById(R.id.starthelp);
        buttonStart.setOnClickListener(v -> StartFill());
        Button buttonClose= dialog.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> EndFill());
        Button buttonListen = dialog2.findViewById(R.id.OK);
        buttonListen.setOnClickListener(v -> Listen());
        Button buttonClose2= dialog2.findViewById(R.id.close2);
        buttonClose2.setOnClickListener(v -> NoListen());

    }

    public void StartFill(){
        dialog.hide();
        task.StartTask();
        //dialog2.show();
    }

    public void EndFill(){
        dialog.hide();
    }

    public void Listen(){
        dialog2.hide();
    }

    public void NoListen(){
        dialog2.hide();
    }

    @Override
    public void RunAction(){
        if (!task.Started || task.finished)
            dialog.show();
        //else
        //    dialog2.show();
    }

    @Override
    public void loadAttributes(String[] attributes){
        textHello = attributes[3];
        int targetX = Integer.valueOf(attributes[1])-1;
        int targetY = Integer.valueOf(attributes[2])-1;
        task.TargetCell = mapActivity.Map.Cells[targetX][targetY];
    }

    @Override
    public void FinishTask(){
        Constants.DATAGAME.setMoney(Constants.DATAGAME.getMoney() +bonus);

        //dialog3.show();
        task.finished = true;


        DialogMessage.showMessage(R.drawable.piligrim,R.drawable.icon_money,"Спасибо, добрый человек. Вот тебе небольшой подарок от меня: 100 монет","+"+String.valueOf(bonus),mapActivity);
    }

}
