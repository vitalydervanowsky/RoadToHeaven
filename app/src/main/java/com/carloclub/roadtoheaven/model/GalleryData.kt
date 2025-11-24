package com.carloclub.roadtoheaven.model

import java.io.Serializable

data class GalleryData(
    val title: String = "У які зал змесціш гэтую карціну?",
    val topics: Map<ClassType, String>,
    val images: List<GalleryImage>,
) : Serializable
