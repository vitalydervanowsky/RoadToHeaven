package com.carloclub.roadtoheaven.model

import java.io.Serializable

data class ClassLessonData(
    val title: String,
    val dialogMessage: String,
    val pages: List<PageData>,
) : Serializable
