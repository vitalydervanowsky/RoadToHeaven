package com.carloclub.roadtoheaven.helper

import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.MapActivity

object TaskUtil {

    fun handleTaskSuccess(mapActivity: MapActivity) {
        Constants.DATAGAME.stones += 1
        mapActivity.updateBar()
        MessageUtil.showSuccessTaskFinishedDialog(mapActivity)
        mapActivity.showRubies()
    }

}
