package com.carloclub.roadtoheaven.MapObjects;

import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Tasks;

public class MapObjectHelpBoy extends MapObject{
    public Tasks task;

    public MapObjectHelpBoy(int x, int y, MapActivity mActivity) {
        super(x, y, mActivity);
        type = "helpboy";
        task = new Tasks((MapObject)this);
        dialog.setContentView(R.layout.dialog_helpboy);

        Button buttonStart = dialog.findViewById(R.id.starthelp);
        buttonStart.setOnClickListener(v -> StartFill());

        Button buttonClose= dialog.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> EndFill());
    }

    private void StartFill() {
        dialog.hide();
        task.StartTask();
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
        //textHello = attributes[3];
        int targetX = Integer.valueOf(attributes[1])-1;
        int targetY = Integer.valueOf(attributes[2])-1;
        task.TargetCell = mapActivity.Map.Cells[targetX][targetY];
    }

    @Override
    public void FinishTask(){

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() +1);


        task.finished = true;

        DialogMessage.showMessage(R.drawable.happyboy,R.drawable.icon_ruby,"За Твоё доброе сердце Ты полчаешь 1 Рубин Помощи","+1",mapActivity);
    }

}
