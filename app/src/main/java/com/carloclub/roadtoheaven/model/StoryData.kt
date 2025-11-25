package com.carloclub.roadtoheaven.model

import java.io.Serializable

/**
Модель данных истории со с диалогом и слайдами
@param type тип истории
@param person персонаж в диалоге
@param title тема урока в школе
@param startDialogInfo диалог стартовый
@param endDialogInfo диалог после просмотра слайдов
@param position текущая страница
@param pages список страниц со слайдами
@param backgroundImageRes фоновое изображение под диалог на старте
@param audioRes аудиофайл с фоновой музыкой
 */
data class StoryData(
    val type: StoryType,
    val person: Person,
    val title: String?,
    val startDialogInfo: DialogInfo,
    val endDialogInfo: DialogInfo?,
    var position: Int,
    val pages: List<PageData>,
    val backgroundImageRes: Int?,
    val audioRes: Int?,
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

data class DialogInfo(
    val message: String,
    val yesButton: String,
    val noButton: String,
) : Serializable
