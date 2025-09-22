package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

public class MapObjectChurch extends MapObject {
    String Tytle;
    int interierID;
    public MapObjectChurch(int X, int Y, MapActivity mActivity){
        super(X, Y, mActivity);
        type = "church";
        dialog.setContentView(R.layout.dialog_church);
        Button buttonStop = dialog.findViewById(R.id.close);
        buttonStop.setOnClickListener(v -> EndFill());
        ObjectMediaPlayer = MediaPlayer.create(mActivity, R.raw.organ);
    }
    public void EndFill(){
        ObjectMediaPlayer.pause();
        dialog.hide();
    }

    @Override
    public void RunAction(){
        ((ImageView)dialog.findViewById(R.id.imageViewInterier)).setImageResource(interierID);
        ((TextView)dialog.findViewById(R.id.textViewTytle)).setText(Tytle);
        dialog.show();
        ObjectMediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes){

            Tytle = attributes[3];
            if (attributes[2]!=null)
                interierID = mapActivity.getResources().getIdentifier(attributes[2],"drawable", mapActivity.getPackageName());

    }
}
