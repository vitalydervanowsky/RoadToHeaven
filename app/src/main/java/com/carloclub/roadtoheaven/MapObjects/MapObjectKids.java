package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.widget.TextView;

import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;
import com.carloclub.roadtoheaven.Victorina;
import com.carloclub.roadtoheaven.helper.MessageUtil;
import com.carloclub.roadtoheaven.helper.TaskUtil;
import com.carloclub.roadtoheaven.helper.TimeUtil;
import com.carloclub.roadtoheaven.model.Person;

import java.util.Calendar;
import java.util.Date;

public class MapObjectKids extends MapObject {
    Victorina victorina;
    MyMap.Question question;
    Date lastSuccess;
    private final Person person = Person.CARLO;

    public MapObjectKids(int x, int y, MapActivity activity) {
        super(x, y, activity);
        type = "kids";
        task = new Task(this);

    }

    private void startFill() {

    }

    public void endFill() {
        dialog.dismiss();
    }


    @Override
    public void runAction() {
        if (!isActual()) { //чаще 3 минут не давать
            MessageUtil.INSTANCE.showFailureDialog(Messages.getMessageGalleryTechnicalBreak(), mapActivity);
            return;
        }

        if (dialog==null) {
            dialog = new Dialog(mapActivity, R.style.FullScreenDialog);
            dialog.setContentView(R.layout.dialog_castom_victorina);

        }
        dialog.show();
        question = mapActivity.map.kidsQuestion.get(0);
        ((TextView) dialog.findViewById(R.id.textViewQuestion)).setText(question.question);
        victorina = new Victorina(this, dialog);
        victorina.loadQuestion(question);
        victorina.showAnswers();
    }

    @Override
    public boolean isActual(){
        //чаще 3 минут не давать
        return !TimeUtil.INSTANCE.isLessThanThreeMinutesPast(lastSuccess);
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }

    @Override
    public void endVictorina(boolean isOK) {
        if (!isOK) {
            //incorrectMediaPlayer.start();
            MessageUtil.INSTANCE.showFailureDialog(Messages.getMessageMistake(), mapActivity);

        } else {
            //triumfMediaPlayer.start();
            TaskUtil.INSTANCE.handleTaskSuccess(mapActivity, person);
            lastSuccess = Calendar.getInstance().getTime();
        }
        dialog.dismiss();

    }

    @Override
    public void finishTask() {


    }

}
