package com.carloclub.roadtoheaven.helper

import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.MapActivity
import com.carloclub.roadtoheaven.model.Person

object TaskUtil {

    fun handleTaskSuccess(mapActivity: MapActivity, person: Person) {
        Constants.DATAGAME.stones += 1
        mapActivity.updateBar()
        MessageUtil.showSuccessTaskFinishedDialog(mapActivity, person)
        mapActivity.showRubies()
    }

}
