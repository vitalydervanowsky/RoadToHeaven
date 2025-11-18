package com.carloclub.roadtoheaven.MapObjects;

import static com.carloclub.roadtoheaven.DialogMessage.showMessage;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.gallery.GalleryHelper;
import com.carloclub.roadtoheaven.model.DialogButton;
import com.carloclub.roadtoheaven.story.StoryHelper;
import com.carloclub.roadtoheaven.story.model.StoryData;

public class MapObjectGallery extends MapObject {
    String title;
    int interiorId;

    public MapObjectGallery(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "gallery";

    }

    @Override
    public void runAction() {
        // todo добавить данные для диалога
        StoryData storyData = StoryHelper.INSTANCE.getStoryData(mapActivity.city);
        showMessage(
                0,
                0,
                storyData.getDialogMessage(),
                null,
                mapActivity,
                R.drawable.kseniya,
                new DialogButton(
                        "Хачу ведаць",
                        () -> GalleryHelper.INSTANCE.showGalleryActivity(mapActivity, mapActivity.city)
                ),
                new DialogButton(
                        "Выйсці",
                        null
                ),
                storyData.getBackgroundImageRes()
        );
    }

    @Override
    public void loadAttributes(String[] attributes) {
        title = attributes[0];
        if (attributes[2] != null) {
            interiorId = mapActivity.getResources().getIdentifier(attributes[2], "drawable", mapActivity.getPackageName());
        }
    }
}
