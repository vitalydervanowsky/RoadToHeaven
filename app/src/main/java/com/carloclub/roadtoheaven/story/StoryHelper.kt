package com.carloclub.roadtoheaven.story

import android.app.Activity
import android.content.Intent
import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.PageData
import com.carloclub.roadtoheaven.model.StoryData

object StoryHelper {
    fun showStoryActivity(activity: Activity, storyData: StoryData) {
        activity.startActivity(Intent(activity, StoryActivity::class.java).apply {
            putExtra(StoryFragment.STORY_DATA_ARG, storyData)
        })
    }

    fun getStoryData(city: City): StoryData? =
        when (city) {
            City.SOKULKA -> getChurchStoryDataForSokulka()
            else -> null
        }

    private fun getChurchStoryDataForSokulka() =
        StoryData(
            position = 0,
            pages = listOf(
                PageData(
                    text = "Падчас Святой Імшы асвечаная Гостыя ўпала на падлогу і святар паклаў яе ў пасудзіну з вадой.",
                    imageRes = R.drawable.sokulka_story_1,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "Праз тыдзень манахіня ўбачыла, што Гостыя амаль растварылася, але на ёй з’явіліся плямы нібыта крыві.",
                    imageRes = R.drawable.sokulka_story_2,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "Улады Касцёла адправілі фрагмент на даследванне.",
                    imageRes = R.drawable.sokulka_story_3,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "Навукоўцы сцвердзілі, што плямы на Гостыі — гэта сапраўдны кавалачак чалавечай сардэчнай мышцы.",
                    imageRes = R.drawable.sokulka_story_4,
                    audioRes = R.raw.organ
                )
            ),
            dialogMessage = "12 кастрычніка 2008 года адбыўся эўхарыстычны цуд у горадзе Сакулка. Хочаш даведацца больш?",
            backgroundImageRes = R.drawable.sokulka_church,
        )
}
