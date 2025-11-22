package com.carloclub.roadtoheaven.MapObjects;

import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

import com.carloclub.roadtoheaven.Constants;
import com.carloclub.roadtoheaven.DialogMessage;
import com.carloclub.roadtoheaven.MapActivity;
import com.carloclub.roadtoheaven.Messages;
import com.carloclub.roadtoheaven.MyMap;
import com.carloclub.roadtoheaven.R;
import com.carloclub.roadtoheaven.Task;
import com.carloclub.roadtoheaven.Victorina;

import java.util.Calendar;
import java.util.Date;

public class MapObjectKids extends MapObject {
    Victorina victorina;
    MyMap.Question question;
    Date lastSuccess;

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
        if (lastSuccess != null && (Calendar.getInstance().getTime().getTime() - lastSuccess.getTime()) < 180000) { //чаще 3 минут не давать
            DialogMessage.showMessage(R.drawable.fail, R.drawable.fail, Messages.getMessageGalleryTechnicalBreak(), "", mapActivity);
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
        if (lastSuccess!=null && (Calendar.getInstance().getTime().getTime()-lastSuccess.getTime())<180000) { //чаще 3 минут не давать
            return false;
        }
        return true;
    }

    @Override
    public void loadAttributes(String[] attributes) {

    }

    @Override
    public void endVictorina(boolean isOK) {
        if (!isOK) {
            //incorrectMediaPlayer.start();
            DialogMessage.showMessage(R.drawable.fail, R.drawable.fail, Messages.getMessageMistake(), "", mapActivity);

        } else {
            //triumfMediaPlayer.start();
            Constants.DATAGAME.setStones(Constants.DATAGAME.getStones() + 1);
            mapActivity.updateBar();

            if (Constants.DATAGAME.getStones() == 7) {
                DialogMessage.showMessage(R.drawable.bridge, R.drawable.stones1, Messages.getMessageGotAllStones(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            } else {
                DialogMessage.showMessage(R.drawable.gratulation, R.drawable.stones1, Messages.getMessageGotStone(), Messages.getMessageHowManyStonesGot() + String.valueOf(Constants.DATAGAME.getStones()), mapActivity);
            }
            lastSuccess = Calendar.getInstance().getTime();
            mapActivity.showRubies();
        }
        dialog.dismiss();

    }

    @Override
    public void finishTask() {


    }

}
