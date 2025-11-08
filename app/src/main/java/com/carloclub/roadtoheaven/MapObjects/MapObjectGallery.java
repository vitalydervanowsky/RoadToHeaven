package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.gallery.GalleryHelper;

public class MapObjectGallery extends MapObject {
    String title;
    int interiorId;

    public MapObjectGallery(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "gallery";

    }

    @Override
    public void runAction() {
        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_church);
            dialog.findViewById(R.id.yesButton).setOnClickListener(v -> {
                dialog.dismiss();
                GalleryHelper.INSTANCE.showGalleryActivity(mapActivity);
            });
            dialog.findViewById(R.id.noButton).setOnClickListener(v -> dialog.dismiss());

        }

        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM | Gravity.START);
        }
        ((TextView) dialog.findViewById(R.id.textView)).setText(title);
        dialog.show();

    }

    @Override
    public void loadAttributes(String[] attributes) {
        title = attributes[0];
        if (attributes[2] != null) {
            interiorId = mapActivity.getResources().getIdentifier(attributes[2], "drawable", mapActivity.getPackageName());
        }
    }
}
