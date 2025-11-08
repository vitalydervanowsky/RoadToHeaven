package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.view.WindowCompat;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;

public class MapObjectSchool extends MapObject {
    public Task task;

    public MapObjectSchool(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "helpboy";
        task = new Task(this);

//
//        Button buttonClose = dialog.findViewById(R.id.close);
//        buttonClose.setOnClickListener(v -> endFill());
    }

    private void startFill() {
        //dialog.dismiss();
        dialog.setContentView(R.layout.dialog_class);
    }

    public void endFill() {
        dialog.dismiss();
    }

    @Override
    public boolean isActual(){
        return !task.isStarted;
    }
    @Override
    public void runAction() {
        if (!task.isStarted) {
            if (dialog==null) {
                dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
                dialog.setContentView(R.layout.dialog_school);

                ImageView back = dialog.findViewById(R.id.imageBack);
                back.setOnClickListener(v -> startFill());
            }

            dialog.show();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            WindowCompat.setDecorFitsSystemWindows(window, false);
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }

    @Override
    public void loadAttributes(String[] attributes) {
        //textHello = attributes[3];

    }

    @Override
    public void finishTask() {

        Constants.DATAGAME.setRubies(Constants.DATAGAME.getRubies() + 1);


        task.isFinished = true;

        DialogMessage.showMessage(R.drawable.happyboy, R.drawable.icon_ruby, Messages.getMessageYouGetRubyHelp(), "+1", mapActivity);
    }

}
