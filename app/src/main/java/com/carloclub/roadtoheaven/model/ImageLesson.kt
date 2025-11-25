package com.carloclub.roadtoheaven.model

import com.carloclub.roadtoheaven.maps.City

data class ImageLesson(
    val id: Int,
    val city: City,
    val title: String,
    val description: String,
    val classType: ClassType,
    val classTitle: String?,
    val schoolImageRes: Int?,
    val galleryImageRes: Int?,
    val audioRes: Int?,
)
