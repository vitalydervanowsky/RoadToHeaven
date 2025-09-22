package com.carloclub.roadtoheaven.MapObjects;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.TextView;

import com.carloclub.roadtoheaven.BridgeActivity;
import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogActivity;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MainActivity;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

import java.util.Timer;
import java.util.TimerTask;

public class MapObjectBridge extends MapObject {
    private TextView TextMoney;

    private TextView TextFuel;
    public MapObjectBridge(int X, int Y, MapActivity mActivity){
        super(X, Y, mActivity);
        type = "bridge";

    }

    public void StartFill(){

    }

    public void EndFill(){

    }

    @Override
    public void RunAction(){
        if (Constants.DATAGAME.getStones()<7) {
            DialogMessage.showMessage(R.drawable.icon_stones, R.drawable.icon_stones, "Недостаточно камней! Чтобы начать строить мост, нужно собрать 7 камней", "Собрано: " + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            return;
        }
        //запускаем строительство моста
        Intent i = new Intent(mapActivity, BridgeActivity.class);
        mapActivity.startActivityForResult(i,0);

    }


}
