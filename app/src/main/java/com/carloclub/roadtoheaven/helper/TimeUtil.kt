package com.carloclub.roadtoheaven.helper

import java.util.Calendar
import java.util.Date

const val THREE_MINUTES = 180_000

object TimeUtil {

    fun isLessThanThreeMinutesPast(lastSuccess: Date?): Boolean =
        lastSuccess != null && (Calendar.getInstance().time.time - lastSuccess.time) < THREE_MINUTES

}
