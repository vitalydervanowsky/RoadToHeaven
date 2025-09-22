package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

public class MapObjectRM extends MapObject {
    public MediaPlayer playerPaterNoster;
    public MediaPlayer playerAveMaria;
    public MapObjectRM(int X, int Y, MapActivity mActivity){
        super(X, Y, mActivity);
        type = "RM";
        dialog.setContentView(R.layout.dialog_rm);
        Button buttonStop = dialog.findViewById(R.id.close);
        buttonStop.setOnClickListener(v -> EndFill());
        playerPaterNoster = MediaPlayer.create(mActivity, R.raw.organ);
        playerAveMaria = MediaPlayer.create(mActivity, R.raw.organ);
        dialog.findViewById(R.id.buttotPaterNoster).setOnClickListener(v -> PaterNoster());
        dialog.findViewById(R.id.buttonAveMaria).setOnClickListener(v -> AveMaria());
    }
    public void EndFill(){
        playerPaterNoster.stop();
        playerAveMaria.stop();
        dialog.hide();
    }

    public void PaterNoster(){
        if (playerPaterNoster.isPlaying())
            playerPaterNoster.stop();
        else
            playerPaterNoster.start();
    }
    public void AveMaria(){
        if (playerAveMaria.isPlaying())
            playerAveMaria.stop();
        else
            playerAveMaria.start();
    }
    @Override
    public void RunAction(){
        dialog.show();
        //ObjectMediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes){

    }
}
