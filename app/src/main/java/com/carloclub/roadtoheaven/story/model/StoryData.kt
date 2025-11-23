package com.carloclub.roadtoheaven.story.model

import java.io.Serializable

data class StoryData(
    var position: Int,
    val pages: List<PageData>,
    val dialogMessage: String? = null,
    val backgroundImageRes: Int? = null,
) : Serializable {
    fun goNext() {
        position++
    }

    fun goBack() {
        position--
    }

    fun isLastPage(): Boolean =
        position + 1 == pages.size
}

data class PageData(
    val text: String,
    val imageRes: Int? = null,
    val audioRes: Int? = null,
) : Serializable
