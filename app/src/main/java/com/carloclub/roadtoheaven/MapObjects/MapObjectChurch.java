package com.carloclub.roadtoheaven.MapObjects;

import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;

public class MapObjectChurch extends MapObject {
    String title;
    int interiorId;

    public MapObjectChurch(int X, int Y, MapActivity activity) {
        super(X, Y, activity);
        type = "church";
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM | Gravity.START);
        }
        dialog.setContentView(R.layout.dialog_church);
        dialog.findViewById(R.id.yesButton).setOnClickListener(v -> {
            dialog.dismiss();
            com.carloclub.roadtoheaven.story.Helper.INSTANCE.showStoryActivity(mapActivity);
        });
        dialog.findViewById(R.id.noButton).setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void runAction() {
        ((TextView) dialog.findViewById(R.id.textView)).setText(title);
        dialog.show();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(mapActivity, R.raw.organ);
        mediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes) {
        title = attributes[0];
        if (attributes[2] != null) {
            interiorId = mapActivity.getResources().getIdentifier(attributes[2], "drawable", mapActivity.getPackageName());
        }
    }
}
