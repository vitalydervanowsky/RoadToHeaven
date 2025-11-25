package com.carloclub.roadtoheaven.story

import android.app.Activity
import android.content.Intent
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.model.DialogInfo
import com.carloclub.roadtoheaven.model.PageData
import com.carloclub.roadtoheaven.model.Person
import com.carloclub.roadtoheaven.model.StoryData
import com.carloclub.roadtoheaven.model.StoryType

object StoryHelper {
    fun showStoryActivityForResult(activity: Activity, storyData: StoryData) {
        activity.startActivityForResult(Intent(activity, StoryActivity::class.java).apply {
            putExtra(StoryFragment.STORY_DATA_ARG, storyData)
        }, 111)
    }

    fun getChurchStoryData(city: City): StoryData =
        when (city) {
            City.SOKOLKA -> getChurchStoryDataForSokolka()
            else -> getChurchStoryDataForSokolka()
        }

    private fun getChurchStoryDataForSokolka() =
        StoryData(
            type = StoryType.CHURCH,
            person = Person.KSENIYA,
            title = null,
            startDialogInfo = DialogInfo(
                message = "12 кастрычніка 2008 года адбыўся эўхарыстычны цуд у горадзе Сакулка. Хочаш даведацца больш?",
                yesButton = "Так",
                noButton = "Нe",
            ),
            endDialogInfo = DialogInfo(
                message = "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
                yesButton = "Выканаць заданне",
                noButton = "Выйсцi з касцёла",
            ),
            position = 0,
            pages = listOf(
                PageData(
                    text = "Падчас Святой Імшы асвечаная Гостыя ўпала на падлогу і святар паклаў яе ў пасудзіну з вадой.",
                    imageRes = R.drawable.sokolka_story_1,
                    audioRes = R.raw.sokolka_story_1
                ),
                PageData(
                    text = "Праз тыдзень манахіня ўбачыла, што Гостыя амаль растварылася, але на ёй з’явіліся плямы нібыта крыві.",
                    imageRes = R.drawable.sokolka_story_2,
                    audioRes = R.raw.sokolka_story_2
                ),
                PageData(
                    text = "Улады Касцёла адправілі фрагмент на даследванне.",
                    imageRes = R.drawable.sokolka_story_3,
                    audioRes = R.raw.sokolka_story_3
                ),
                PageData(
                    text = "Навукоўцы сцвердзілі, што плямы на Гостыі — гэта сапраўдны кавалачак чалавечай сардэчнай мышцы.",
                    imageRes = R.drawable.sokolka_story_4,
                    audioRes = R.raw.sokolka_story_4
                )
            ),
            backgroundImageRes = R.drawable.sokolka_church,
            audioRes = R.raw.organ,
        )

    fun getWellStoryData(city: City): StoryData =
        when (city) {
            City.SOKOLKA -> getWellStoryDataForSokolka()
            else -> getWellStoryDataForSokolka()
        }

    private fun getWellStoryDataForSokolka(): StoryData =
        StoryData(
            type = StoryType.WELL,
            person = Person.FATHER_EUGENE,
            title = null,
            startDialogInfo = DialogInfo(
                message = "hello",
                yesButton = "yes",
                noButton = "no",
            ),
            endDialogInfo = DialogInfo(
                message = "congrats",
                yesButton = "yes",
                noButton = "no",
            ),
            position = 0,
            pages = listOf(
                // todo add father Eugene story here
                PageData(
                    text = "Падчас Святой Імшы асвечаная Гостыя ўпала на падлогу і святар паклаў яе ў пасудзіну з вадой.",
                    imageRes = R.drawable.sokolka_story_1,
                    audioRes = R.raw.sokolka_story_1
                ),
                PageData(
                    text = "Падчас Святой Імшы асвечаная Гостыя ўпала на падлогу і святар паклаў яе ў пасудзіну з вадой.",
                    imageRes = R.drawable.sokolka_story_1,
                    audioRes = R.raw.sokolka_story_1
                ),
            ),
            backgroundImageRes = R.drawable.well,
            audioRes = null,
        )
}
