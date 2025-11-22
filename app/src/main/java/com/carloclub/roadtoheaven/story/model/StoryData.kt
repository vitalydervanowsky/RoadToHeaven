package com.carloclub.roadtoheaven.story.model

import java.io.Serializable

data class StoryData(
    var position: Int,
    val pages: List<PageData>,
    val dialogMessage: String? = null,
    val backgroundImageRes: Int? = null,
) : Serializable

data class PageData(
    val text: String,
    val imageRes: Int? = null,
    val audioRes: Int? = null,
) : Serializable

fun List<PageData>.addFinalPage(text: String): List<PageData> =
    this.plus(
        PageData(text = text)
    )
