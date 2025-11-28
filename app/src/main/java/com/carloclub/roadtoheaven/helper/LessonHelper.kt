package com.carloclub.roadtoheaven.helper

import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.mapper.toGalleryImage
import com.carloclub.roadtoheaven.mapper.toPageData
import com.carloclub.roadtoheaven.model.ClassType
import com.carloclub.roadtoheaven.model.DialogInfo
import com.carloclub.roadtoheaven.model.GalleryData
import com.carloclub.roadtoheaven.model.ImageLesson
import com.carloclub.roadtoheaven.model.Person
import com.carloclub.roadtoheaven.model.StoryData
import com.carloclub.roadtoheaven.model.StoryType

// todo добавить переводы и логику получения строк
object LessonHelper {

    fun getStoryDataForSchool(city: City?, classType: ClassType): StoryData =
        StoryData(
            type = StoryType.SCHOOL,
            person = Person.OLGA,
            title = "Тэма ўрока:\n\n" + getLessonTitles(city)[classType].orEmpty(),
            startDialogInfo = DialogInfo(
                message = getLessonDialogMessages(city)[classType].orEmpty(),
                yesButton = "Хачу ведаць!",
                noButton = "Выйсці з класа",
            ),
            endDialogInfo = DialogInfo(
                message = "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
                yesButton = "Выканаць заданне",
                noButton = "Выйсці з класа",
            ),
            position = 0,
            pages = getImageLessons(city)
                .filter { it.classType == classType }
                .map { it.toPageData() },
            backgroundImageRes = R.drawable.school_lesson,
            audioRes = null,
        )

    private fun getImageLessons(city: City?): List<ImageLesson> =
        listOf(
            ImageLesson(
                id = 1,
                city = City.SOKOLKA,
                title = "Настаўляць грэшнікаў",
                description = "1. Настаўляць грэшнікаў — гэта значыць з любові дапамагаць чалавеку ўбачыць зло і вярнуцца да Бога.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_1,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_1,
                audioRes = R.raw.mercy_act_soul_1,
            ),
            ImageLesson(
                id = 2,
                city = City.SOKOLKA,
                title = "Вучыць тых, хто не ведае",
                description = "2. Вучыць тых, хто не ведае — паказваць Божы шлях тым, хто згубіўся або не ўпэўнены.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_2,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_2,
                audioRes = R.raw.mercy_act_soul_2
            ),
            ImageLesson(
                id = 3,
                city = City.SOKOLKA,
                title = "Раіць тым, хто сумняваецца",
                description = "3. Раіць тым, хто сумняваецца — дзяліцца мудрасцю і дапамагаць прыняць правільнае рашэнне.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_3,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_3,
                audioRes = R.raw.mercy_act_soul_3
            ),
            ImageLesson(
                id = 4,
                city = City.SOKOLKA,
                title = "Суцяшаць засмучаных",
                description = "4. Суцяшаць засмучаных — быць побач і даваць чалавеку адчуць, што ён не адзін.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_4,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_4,
                audioRes = R.raw.mercy_act_soul_4
            ),
            ImageLesson(
                id = 5,
                city = City.SOKOLKA,
                title = "Зносіць цярпліва знявагу",
                description = "5. Зносіць цярпліва знявагу — адказваць на крыўду не злосцю, а малітваю і прабачэннем.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_5,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_5,
                audioRes = R.raw.mercy_act_soul_5
            ),
            ImageLesson(
                id = 6,
                city = City.SOKOLKA,
                title = "Дараваць ахвотна крыўду",
                description = "6. Дараваць ахвотна крыўду — адпускаць боль і давяраць справядлівасць Богу.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_6,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_6,
                audioRes = R.raw.mercy_act_soul_6
            ),
            ImageLesson(
                id = 7,
                city = City.SOKOLKA,
                title = "Маліцца за жывых і памерлых",
                description = "7. Маліцца за жывых і памерлых — даручаць усіх Богу, каб яны былі ў Яго міласэрных руках.",
                classType = ClassType.A,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.A],
                schoolImageRes = R.drawable.mercy_act_soul_school_7,
                galleryImageRes = R.drawable.mercy_act_soul_gallery_7,
                audioRes = R.raw.mercy_act_soul_7
            ),
            ImageLesson(
                id = 8,
                city = City.SOKOLKA,
                title = "Накарміць галодных",
                description = "1. Накарміць галодных — дзяліцца ежай і падтрымліваць тых, хто церпіць ад нястачы.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_1,
                galleryImageRes = R.drawable.mercy_act_body_gallery_1,
                audioRes = R.raw.mercy_act_body_1
            ),
            ImageLesson(
                id = 9,
                city = City.SOKOLKA,
                title = "Напаіць сасмаглых",
                description = "2. Напаіць сасмаглых — дапамагчы тым, хто мае патрэбу ў вадзе, і ўдзячна ставіцца да гэтага дара.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_2,
                galleryImageRes = R.drawable.mercy_act_body_gallery_2,
                audioRes = R.raw.mercy_act_body_2
            ),
            ImageLesson(
                id = 10,
                city = City.SOKOLKA,
                title = "Адзець нагога",
                description = "3. Адзець нагіх — дзяліцца адзеннем з тымі, хто не мае неабходнага, убачыўшы ў іх Езуса.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_3,
                galleryImageRes = R.drawable.mercy_act_body_gallery_3,
                audioRes = R.raw.mercy_act_body_3
            ),
            ImageLesson(
                id = 11,
                city = City.SOKOLKA,
                title = "Прыняць у дом падарожных",
                description = "4. Прыняць у дом падарожных — быць чулым і спагадлівым да бяздомных і тых, хто без прытулку.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_4,
                galleryImageRes = R.drawable.mercy_act_body_gallery_4,
                audioRes = R.raw.mercy_act_body_4
            ),
            ImageLesson(
                id = 12,
                city = City.SOKOLKA,
                title = "Наведаць хворых",
                description = "5. Адведаць хворых — прыносіць падтрымку і надзею тым, хто пакутуе ад хваробы.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_5,
                galleryImageRes = R.drawable.mercy_act_body_gallery_5,
                audioRes = R.raw.mercy_act_body_5
            ),
            ImageLesson(
                id = 13,
                city = City.SOKOLKA,
                title = "Суцяшаць вязняў",
                description = "6. Суцяшаць вязняў — маліцца і даваць надзею тым, хто пазбаўлены свабоды.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_6,
                galleryImageRes = R.drawable.mercy_act_body_gallery_6,
                audioRes = R.raw.mercy_act_body_6
            ),
            ImageLesson(
                id = 14,
                city = City.SOKOLKA,
                title = "Пахаваць памерлых",
                description = "7. Пахаваць памерлых — маліцца за памерлых і з пашанай даглядаць іх магілы.",
                classType = ClassType.B,
                classTitle = getLessonTitles(City.SOKOLKA)[ClassType.B],
                schoolImageRes = R.drawable.mercy_act_body_school_7,
                galleryImageRes = R.drawable.mercy_act_body_gallery_7,
                audioRes = R.raw.mercy_act_body_7
            ),
        ).filter { it.city == city }

    private fun getLessonTitles(city: City?): Map<ClassType, String> =
        when (city) {
            City.SOKOLKA -> mapOf(
                ClassType.A to "Учынкі міласэрнасці для душы бліжняга",
                ClassType.B to "Учынкі міласэрнасці для цела бліжняга"
            )

            else -> mapOf()
        }

    private fun getLessonDialogMessages(city: City?): Map<ClassType, String> =
        when (city) {
            City.SOKOLKA -> mapOf(
                ClassType.A to "Сёння на занятку мы пазнаёмiмся з учынкамi мiласэрнасцi для душы",
                ClassType.B to "Сёння на занятку мы пазнаёмiмся з учынкамi мiласэрнасцi для цела"
            )

            else -> mapOf()
        }

    fun getGalleryData(city: City?): GalleryData =
        GalleryData(
            topics = getLessonTitles(city),
            images = getImageLessons(city).getRandomGalleryImages()
                .map { it.toGalleryImage() },
        )

    private fun List<ImageLesson>.getRandomGalleryImages(n: Int = 7): List<ImageLesson> {
        if (n <= 0) return emptyList()
        if (n >= size) return this.shuffled()
        return shuffled().take(n.coerceAtMost(size))
    }
}
