package com.carloclub.roadtoheaven.mapper

import com.carloclub.roadtoheaven.model.GalleryImage
import com.carloclub.roadtoheaven.model.ImageLesson
import com.carloclub.roadtoheaven.model.PageData

fun ImageLesson.toPageData(): PageData =
    PageData(
        text = this.description,
        imageRes = this.schoolImageRes,
        audioRes = this.audioRes,
    )

fun ImageLesson.toGalleryImage(): GalleryImage =
    GalleryImage(
        id = this.id,
        imageRes = this.galleryImageRes,
        title = this.title,
        classType = this.classType,
    )
