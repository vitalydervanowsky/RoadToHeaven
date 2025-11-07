package com.carloclub.roadtoheaven.gallery.model

import java.io.Serializable

data class GalleryImage(
    val id: Int,
    val imageRes: Int,
    val title: String,
    val correctSide: Side,
    var state: State = State.DEFAULT,
) : Serializable
