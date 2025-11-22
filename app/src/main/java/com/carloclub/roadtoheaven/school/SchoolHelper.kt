package com.carloclub.roadtoheaven.school

import com.carloclub.roadtoheaven.City
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.school.model.ClassLessonData
import com.carloclub.roadtoheaven.school.model.ClassType
import com.carloclub.roadtoheaven.story.model.PageData

object SchoolHelper {

    fun getLessonData(city: City?, classType: ClassType): ClassLessonData? =
        when (city) {
            City.SOKULKA -> getLessonDataForSokulkaAccordingClassType(classType)
            null -> null
        }

    private fun getLessonDataForSokulkaAccordingClassType(classType: ClassType): ClassLessonData =
        when (classType) {
            ClassType.A -> getAClassLessonDataForSokulka()
            ClassType.B -> getBClassLessonDataForSokulka()
        }

    private fun getAClassLessonDataForSokulka(): ClassLessonData =
        ClassLessonData(
            title = "Учынкі міласэрнасці для душы бліжняга",
            dialogMessage = "Сёння на занятку мы пазнаемiмся з учынкамi мiласэрнасцi для душы",
            pages = listOf(
                PageData(
                    text = "1. Настаўляць грэшнікаў — гэта значыць з любові дапамагаць чалавеку ўбачыць зло і вярнуцца да Бога.",
                    imageRes = R.drawable.galery1_8,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "2. Вучыць тых, хто не ведае — паказваць Божы шлях тым, хто згубіўся або не ўпэўнены.",
                    imageRes = R.drawable.galery1_9,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "3. Раіць тым, хто сумняваецца — дзяліцца мудрасцю і дапамагаць прыняць правільнае рашэнне.",
                    imageRes = R.drawable.galery1_10,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "4. Суцяшаць засмучаных — быць побач і даваць чалавеку адчуць, што ён не адзін.",
                    imageRes = R.drawable.galery1_11,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "5. Зносіць цярпліва знявагу — адказваць на крыўду не злосцю, а малітваю і прабачэннем.",
                    imageRes = R.drawable.galery1_12,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "6. Дараваць ахвотна крыўду — адпускаць боль і давяраць справядлівасць Богу.",
                    imageRes = R.drawable.galery1_13,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "7.Маліцца за жывых і памерлых — даручаць усіх Богу, каб яны былі ў Яго міласэрных руках.",
                    imageRes = R.drawable.galery1_14,
                    audioRes = R.raw.organ
                ),
            )
        ).addFinalPage()

    private fun getBClassLessonDataForSokulka(): ClassLessonData =
        ClassLessonData(
            title = "Учынкі міласэрнасці для цела бліжняга",
            dialogMessage = "Сёння на занятку мы пазнаемiмся з учынкамi мiласэрнасцi для цела",
            pages = listOf(
                PageData(
                    text = "1. Накарміць галодных — дзяліцца ежай і падтрымліваць тых, хто церпіць ад нястачы.",
                    imageRes = R.drawable.galery1_1,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "2. Напаіць сасмаглых — дапамагчы тым, хто мае патрэбу ў вадзе, і ўдзячна ставіцца да гэтага дара.",
                    imageRes = R.drawable.galery1_2,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "3. Адзець нагіх — дзяліцца адзеннем з тымі, хто не мае неабходнага, убачыўшы ў іх Езуса.",
                    imageRes = R.drawable.galery1_3,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "4. Прыняць у дом падарожных — быць чулым і спагадлівым да бяздомных і тых, хто без прытулку.",
                    imageRes = R.drawable.galery1_4,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "5. Адведаць хворых — прыносіць падтрымку і надзею тым, хто пакутуе ад хваробы.",
                    imageRes = R.drawable.galery1_6,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "6. Суцяшаць вязняў — маліцца і даваць надзею тым, хто пазбаўлены свабоды.",
                    imageRes = R.drawable.galery1_5,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "7. Пахаваць памерлых — маліцца за памерлых і з пашанай даглядаць іх магілы.",
                    imageRes = R.drawable.galery1_7,
                    audioRes = R.raw.organ
                ),
            )
        ).addFinalPage()

    private fun ClassLessonData.addFinalPage(): ClassLessonData =
        this.copy(
            pages = this.pages.plus(
                PageData(text = "Вiншуем! Урок скончаны")
            )
        )
}