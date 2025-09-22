package com.carloclub.roadtoheaven.MapObjects;

import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Tasks;

public class MapObjectHunger extends MapObject{
    public Tasks task;

    public MapObjectHunger(int x, int y, MapActivity mActivity) {
        super(x, y, mActivity);
        type = "hunger";
        task = new Tasks((MapObject)this);
        dialog.setContentView(R.layout.dialog_hunger);

        Button buttonStart = dialog.findViewById(R.id.starthelp);
        buttonStart.setOnClickListener(v -> StartFill());

        Button buttonClose= dialog.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> EndFill());
    }

    private void StartFill() {
        if (Constants.DATAGAME.getBurgers()<1){
            DialogMessage.showMessage(R.drawable.fail,R.drawable.burger,"В рюкзаке нет бургеров. Но их можно купить в бургерной","0",mapActivity);
            return;
        }

        Constants.DATAGAME.setBurgers(Constants.DATAGAME.getBurgers()-1);

        dialog.hide();
        task.StartTask();
        task.FinishTask();
    }

    public void EndFill(){
        dialog.hide();
    }

    @Override
    public void RunAction(){
        if (!task.Started)
            dialog.show();
    }

    @Override
    public void loadAttributes(String[] attributes){

    }

    @Override
    public void FinishTask(){

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() +1);


        task.finished = true;

        DialogMessage.showMessage(R.drawable.hunger,R.drawable.icon_ruby,"За Твоё доброе сердце Ты полчаешь 1 Рубин Помощи","+1",mapActivity);
    }

}
