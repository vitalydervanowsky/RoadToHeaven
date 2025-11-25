package com.carloclub.roadtoheaven.helper

import android.app.Activity
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.DialogMessage
import com.carloclub.roadtoheaven.Messages
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.DialogButton
import com.carloclub.roadtoheaven.model.Person

object MessageUtil {
    fun showDialog(
        message: String,
        activity: Activity,
        personImageRes: Int?,
        yesDialogButton: DialogButton,
        noDialogButton: DialogButton,
    ) {
        DialogMessage.showMessage(
            0,
            0,
            message,
            null,
            activity,
            personImageRes ?: 0,
            yesDialogButton,
            noDialogButton,
        )
    }

    fun showFailureDialog(message: String, mapActivity: Activity) {
        DialogMessage.showMessage(
            R.drawable.fail,
            0,//R.drawable.fail,
            message,
            null,//"",
            mapActivity
        )
    }

    fun showSuccessTaskFinishedDialog(
        mapActivity: Activity,
        person: Person,
    ) {
        if (Constants.DATAGAME.stones == 7) {
            DialogMessage.showMessage(
                R.drawable.bridge,
                R.drawable.stones1,
                Messages.getMessageGotAllStones(),
                Messages.getMessageHowManyStonesGot() + Constants.DATAGAME.stones.toString(),
                mapActivity
            )
        } else {
            DialogMessage.showMessage(
                R.drawable.gratulation,
                R.drawable.stones1,
                Messages.getMessageGotStone(),
                Messages.getMessageHowManyStonesGot() + Constants.DATAGAME.stones.toString(),
                mapActivity,
                person.withStoneImageRes,
            )
        }
    }
}
