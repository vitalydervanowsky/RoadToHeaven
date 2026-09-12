package com.carloclub.roadtoheaven.model

import java.io.Serializable

data class GalleryImage(
    val id: Int,
    val imageRes: String?,
    val title: String,
    val classType: ClassType,
    var state: State = State.DEFAULT,
) : Serializable
