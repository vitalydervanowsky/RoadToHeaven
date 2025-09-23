package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

public class MapObjectChurch extends MapObject {
    String title;
    int interiorId;

    public MapObjectChurch(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "church";
        dialog.setContentView(R.layout.dialog_church);
        Button buttonStop = dialog.findViewById(R.id.close);
        buttonStop.setOnClickListener(v -> endFill());
        mediaPlayer = MediaPlayer.create(activity, R.raw.organ);
    }

    public void endFill() {
        mediaPlayer.pause();
        dialog.dismiss();
    }

    @Override
    public void runAction() {
        ((ImageView) dialog.findViewById(R.id.imageViewInterier)).setImageResource(interiorId);
        ((TextView) dialog.findViewById(R.id.textViewTytle)).setText(title);
        dialog.show();
        mediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes) {
        title = attributes[3];
        if (attributes[2] != null) {
            interiorId = mapActivity.getResources().getIdentifier(attributes[2], "drawable", mapActivity.getPackageName());
        }
    }
}
