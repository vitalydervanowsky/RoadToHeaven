package com.carloclub.roadtoheaven.story

import android.app.Activity
import android.content.Intent
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.databases.Mission
import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.model.*

object StoryHelper {
    fun showStoryActivityForResult(activity: Activity, storyData: StoryData) {
        activity.startActivityForResult(Intent(activity, StoryActivity::class.java).apply {
            putExtra(StoryFragment.STORY_DATA_ARG, storyData)
        }, 111)
    }

    fun getChurchStoryData(mission: Mission): StoryData =
            getChurchStoryDataForMission(mission)


    private fun getChurchStoryDataForMission(mission: Mission) =
        StoryData(
            type = StoryType.CHURCH,
            person = Person.KSENIYA,
            title = null,
            startDialogInfo = DialogInfo(
                message = mission.getLessonHello("church"),
                yesButton = "Так",
                noButton = "Нe",
            ),
            endDialogInfo = DialogInfo(
                message = "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
                yesButton = "Выканаць заданне",
                noButton = "Выйсці з касцёла",
            ),
            position = 0,
            pages = mission!!.getLessonData("church"),
            backgroundImageRes = R.drawable.sokolka_church,
            audioRes = R.raw.organ,
        )

    fun getWellStoryData(mission: Mission): StoryData =
            getWellStoryDataForMission(mission)

    private fun getWellStoryDataForMission(mission: Mission): StoryData =
        StoryData(
            type = StoryType.WELL,
            person = Person.FATHER_EUGENE,
            title = null,
            startDialogInfo = DialogInfo(
                message = "Супакой i дабро! Вітаю каля студні і хачу распавесці новую гісторыю",
                yesButton = "Хачу паслухаць",
                noButton = "Выйсці",
            ),
            endDialogInfo = DialogInfo(
                message = "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
                yesButton = "Здабыць камень",
                noButton = "Выйсці",
            ),
            position = 0,
            pages = mission!!.getLessonData("well"),
            backgroundImageRes = R.drawable.well,
            audioRes = null,
        )
}
