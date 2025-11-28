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
                message = "Супакой i дабро! Вітаю каля студні і хачу распавесці новую гісторыю",
                yesButton = "Хачу паслухаць",
                noButton = "Выйсцi",
            ),
            endDialogInfo = DialogInfo(
                message = "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
                yesButton = "Здабыць камень",
                noButton = "Выйсцi",
            ),
            position = 0,
            pages = listOf(
                PageData(
                    text = "Міша прынес на дзіцячую пляцоўку яблыкі. Яго сябра Косця запытаўся ў яго: \"Дзе ты ўзяў такія смачныя яблыкі?\"",
                    imageRes = R.drawable.sokolka_well_1,
                    audioRes = null
                ),
                PageData(
                    text = "Міша расказаў , што сарваў яблыкі у садзе бабулі, якая ўсе роўна нічога не заўважыла.",
                    imageRes = R.drawable.sokolka_well_2,
                    audioRes = null
                ),
                PageData(
                    text = "Косця сказаў сябру: \"Так рабіць нельга! Бо красці  гэта грэх. Ты павінен вярнуць яблыкі бабулі і папрасіць прабачэння.\"",
                    imageRes = R.drawable.sokolka_well_3,
                    audioRes = null
                ),
                PageData(
                    text = "Непроста гэта было зрабіць, але ў Мішы атрымалася! Ен папрасіў прабачэння, і паабяцаў так болей не рабіць.",
                    imageRes = R.drawable.sokolka_well_4,
                    audioRes = null
                ),
                PageData(
                    text = "Бабуля прабачыла хлопчыка. І нават пачаставала яго яблыкамі.",
                    imageRes = R.drawable.sokolka_well_5,
                    audioRes = null
                ),
                PageData(
                    text = "У нядзелю Міша прыступіў да сакрамэнту споведзі.",
                    imageRes = R.drawable.sokolka_well_6,
                    audioRes = null
                ),
            ),
            backgroundImageRes = R.drawable.well,
            audioRes = null,
        )
}
