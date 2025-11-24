package com.carloclub.roadtoheaven.MapObjects;

import static com.carloclub.roadtoheaven.DialogMessage.showMessage;

import android.app.Activity;
import android.content.Intent;

import com.carloclub.roadtoheaven.City;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.gallery.GalleryActivity;
import com.carloclub.roadtoheaven.gallery.GalleryFragment;
import com.carloclub.roadtoheaven.helper.LessonHelper;
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
                        () -> showGalleryActivity(mapActivity, mapActivity.city)
                ),
                new DialogButton(
                        "Выйсці",
                        null
                ),
                storyData.getBackgroundImageRes()
        );
    }


    private void showGalleryActivity(Activity activity, City city) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.putExtra(GalleryFragment.GALLERY_IMAGES_ARG, LessonHelper.INSTANCE.getGalleryData(city));
        activity.startActivity(intent);
    }

    @Override
    public void loadAttributes(String[] attributes) {
        title = attributes[0];
        if (attributes[2] != null) {
            interiorId = mapActivity.getResources().getIdentifier(attributes[2], "drawable", mapActivity.getPackageName());
        }
    }
}
