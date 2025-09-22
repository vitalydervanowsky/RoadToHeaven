package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.media.MediaPlayer;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Tasks;

public class MapObject {
    public int X;
    public int Y;
    public String type;
    public Dialog dialog;
    public MapActivity mapActivity;
    public Tasks Task;
    public MediaPlayer ObjectMediaPlayer;

    public MapObject(int x, int y, MapActivity mActivity){
        this.X = x;
        this.Y = y;
        mapActivity =mActivity;
        dialog = new Dialog(mapActivity);

    }


    public void RunAction(){

    }
    public void FinishTask(){

    }
    public void updateData(){

    }
    public void loadAttributes(String[] attributes){

    }

}
