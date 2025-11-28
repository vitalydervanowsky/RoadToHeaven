package com.carloclub.roadtoheaven.MapObjects;

import static com.carloclub.roadtoheaven.DialogMessage.showMessage;

import android.app.Activity;
import android.content.Intent;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.gallery.GalleryActivity;
import com.carloclub.roadtoheaven.gallery.GalleryFragment;
import com.carloclub.roadtoheaven.helper.LessonHelper;
import com.carloclub.roadtoheaven.helper.MessageUtil;
import com.carloclub.roadtoheaven.helper.TimeUtil;
import com.carloclub.roadtoheaven.maps.City;
import com.carloclub.roadtoheaven.model.DialogButton;

import java.util.Calendar;

public class MapObjectGallery extends MapObject {

    public MapObjectGallery(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "gallery";
    }

    @Override
    public void runAction() {
        if (!isActual()) { //чаще 3 минут не давать
            MessageUtil.INSTANCE.showFailureDialog(Messages.getMessageGalleryTechnicalBreak(), mapActivity);
            return;
        }
        // todo добавить данные для диалога
        showMessage(
                0,
                0,
                "Вiтаем! Вы зайшлi ў галерэю",
                null,
                mapActivity,
                R.drawable.kseniya,
                new DialogButton(
                        "Выканаць заданне",
                        () -> showGalleryActivity(mapActivity, mapActivity.city)
                ),
                new DialogButton(
                        "Выйсці",
                        null
                )
        );
    }

    @Override
    public boolean isActual() {
        //чаще 3 минут не давать
        return !TimeUtil.INSTANCE.isLessThanThreeMinutesPast(lastSuccess);
    }

    private void showGalleryActivity(Activity activity, City city) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.putExtra(GalleryFragment.GALLERY_IMAGES_ARG, LessonHelper.INSTANCE.getGalleryData(city));
        activity.startActivity(intent);
    }

    @Override
    public void finishTask() {
        lastSuccess = Calendar.getInstance().getTime();
        mapActivity.showRubies();

    }
}
