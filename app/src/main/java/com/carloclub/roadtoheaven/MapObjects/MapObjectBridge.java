package com.carloclub.roadtoheaven.MapObjects;

import android.content.Intent;
import android.widget.TextView;

import com.carloclub.roadtoheaven.BridgeActivity;
import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

public class MapObjectBridge extends MapObject {
    public MapObjectBridge(int X, int Y, MapActivity activity){
        super(X, Y, activity);
        type = "bridge";

    }


    @Override
    public void runAction(){
        if ((Constants.DATAGAME.getStones()+Constants.DATAGAME.getRubies())<7) {
            DialogMessage.showMessage(R.drawable.icon_stones, R.drawable.icon_stones, "Недостаточно камней! Чтобы начать строить мост, нужно собрать 7 камней", "Собрано: " + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            return;
        }
        //запускаем строительство моста
        Intent i = new Intent(mapActivity, BridgeActivity.class);
        mapActivity.startActivityForResult(i,0);

    }


}
