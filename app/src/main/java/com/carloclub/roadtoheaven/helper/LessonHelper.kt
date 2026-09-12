package com.carloclub.roadtoheaven.helper

import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.databases.Mission
import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.model.*

// todo добавить переводы и логику получения строк
object LessonHelper {

    fun getStoryDataForSchool(mission: Mission?, classType: ClassType): StoryData =
        StoryData(
            type = StoryType.SCHOOL,
            person = Person.OLGA,
            title = "Тэма ўрока:\n\n" + mission?.getLessonTitles(if (classType === ClassType.A) "classA" else "classB").orEmpty(),
            startDialogInfo = DialogInfo(
                message = mission?.getLessonHello(if (classType === ClassType.A) "classA" else "classB").orEmpty(),
                yesButton = "Хачу ведаць!",
                noButton = "Выйсці з класа",
            ),
            endDialogInfo = DialogInfo(
                message = "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
                yesButton = "Выканаць заданне",
                noButton = "Выйсці з класа",
            ),
            position = 0,
            pages = mission!!.getLessonData(if (classType === ClassType.A) "classA" else "classB"),
            backgroundImageRes = R.drawable.school_lesson,
            audioRes = null,
        )


    private fun getLessonTitles(mission: Mission?): Map<ClassType, String> =
         mapOf(
                ClassType.A to mission!!.getLessonTitles("classA"),
                ClassType.B to mission!!.getLessonTitles("classB")
            )



    fun getGalleryData(mission: Mission?): GalleryData =
        GalleryData(
            topics = getLessonTitles(mission),
            images = mission!!.getGalleryData(),
        )

    private fun List<ImageLesson>.getRandomGalleryImages(n: Int = 7): List<ImageLesson> {
        if (n <= 0) return emptyList()
        if (n >= size) return this.shuffled()
        return shuffled().take(n.coerceAtMost(size))
    }
}
