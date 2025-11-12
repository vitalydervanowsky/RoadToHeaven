package com.carloclub.roadtoheaven.school.model

import com.carloclub.roadtoheaven.story.model.PageData
import java.io.Serializable

data class ClassLessonData(
    val title: String,
    val dialogMessage: String,
    val pages: List<PageData>,
) : Serializable
