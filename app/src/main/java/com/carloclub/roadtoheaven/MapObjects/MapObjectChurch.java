package com.carloclub.roadtoheaven.MapObjects;

import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.model.DialogButton;
import com.carloclub.roadtoheaven.story.StoryHelper;
import com.carloclub.roadtoheaven.story.model.StoryData;

public class MapObjectChurch extends MapObject {
    String title;

    public MapObjectChurch(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "church";
    }

    @Override
    public void runAction() {
        StoryData storyData = StoryHelper.INSTANCE.getStoryData(mapActivity.city);
        DialogMessage.showMessage(
                0,
                0,
                storyData.getDialogMessage(),
                null,
                mapActivity,
                R.drawable.kseniya,
                new DialogButton(
                        "Хачу ведаць",
                        () -> StoryHelper.INSTANCE.showStoryActivity(mapActivity, storyData)
                ),
                new DialogButton(
                        "Выйсці",
                        null
                ),
                storyData.getBackgroundImageRes()
        );
//        if (dialog==null) {
//            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
//            dialog.setContentView(R.layout.dialog_church);
//            dialog.findViewById(R.id.yesButton).setOnClickListener(v -> {
//                dialog.dismiss();
//                StoryHelper.INSTANCE.showStoryActivity(mapActivity);
//            });
//            dialog.findViewById(R.id.noButton).setOnClickListener(v -> dialog.dismiss());
//        }
//
//        Window window = dialog.getWindow();
//        if (window != null) {
//            window.setGravity(Gravity.BOTTOM | Gravity.START);
//        }
//
//        ((TextView) dialog.findViewById(R.id.textView)).setText(title);
//        dialog.show();
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//        }
//        mediaPlayer = MediaPlayer.create(mapActivity, R.raw.organ);
//        mediaPlayer.start();
    }

    @Override
    public void loadAttributes(String[] attributes) {
        title = attributes[0];
    }
}
