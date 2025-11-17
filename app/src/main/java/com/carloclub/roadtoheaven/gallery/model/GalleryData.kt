package com.carloclub.roadtoheaven.gallery.model

import java.io.Serializable

data class GalleryData(
    val title: String,
    val leftSideDescription: String,
    val rightSideDescription: String,
    val images: List<GalleryImage>,
) : Serializable
