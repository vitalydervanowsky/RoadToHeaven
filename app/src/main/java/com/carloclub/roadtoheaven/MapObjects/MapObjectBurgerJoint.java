package com.carloclub.roadtoheaven.MapObjects;

import android.widget.Button;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Tasks;

public class MapObjectBurgerJoint extends MapObject{
    public Tasks task;

    public MapObjectBurgerJoint(int x, int y, MapActivity mActivity) {
        super(x, y, mActivity);
        type = "BurgerJoint";
        task = new Tasks((MapObject)this);
        dialog.setContentView(R.layout.dialog_burger);

        Button buttonStart = dialog.findViewById(R.id.buttonPay);
        buttonStart.setOnClickListener(v -> pay());

        Button buttonClose= dialog.findViewById(R.id.close);
        buttonClose.setOnClickListener(v -> EndFill());
    }

    private void pay() {
        int m = Constants.DATAGAME.getMoney();
        if (m<5){
            DialogMessage.showMessage(R.drawable.fail,R.drawable.icon_money,"Недостаточно средств для покупки бургера",String.valueOf(m)+"p.",mapActivity);
            return;
        }
        Constants.DATAGAME.setMoney(m-5);

        Constants.DATAGAME.setBurgers(Constants.DATAGAME.getBurgers()+1);
    }

    public void EndFill(){
        dialog.hide();
    }

    @Override
    public void RunAction(){
            dialog.show();
    }

    @Override
    public void loadAttributes(String[] attributes){

    }

    @Override
    public void FinishTask(){

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() +1);


        task.finished = true;

        DialogMessage.showMessage(R.drawable.happyboy,R.drawable.icon_ruby,"За Твоё доброе сердце Ты полчаешь 1 Рубин Помощи","+1",mapActivity);
    }

}
