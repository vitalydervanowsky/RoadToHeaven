package com.carloclub.roadtoheaven.gallery

import android.app.Activity
import android.content.Intent
import com.carloclub.roadtoheaven.City
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.gallery.model.GalleryData
import com.carloclub.roadtoheaven.gallery.model.Side
import com.carloclub.roadtoheaven.gallery.model.GalleryImage

object GalleryHelper {
    fun showGalleryActivity(activity: Activity, city: City?) {
        activity.startActivity(
            Intent(activity, GalleryActivity::class.java).apply {
                putExtra(GalleryFragment.GALLERY_IMAGES_ARG, getGalleryData(city))
            }
        )
    }

    private fun getGalleryData(city: City?): GalleryData? =
        when (city) {
            City.SOKULKA -> getGalleryDataForSokulka()
            null -> null
        }

    private fun getGalleryDataForSokulka(): GalleryData =
        GalleryData(
            title = "В какой зал поместить эту картину?",
            leftSideDescription = "Учынкі міласэрнасці для цела бліжняга",
            rightSideDescription = "Учынкі міласэрнасці для душы бліжняга",
            images = listOf(// здесь пишем картинки в прямом порядке, как они будут идти одна одной
                GalleryImage(1, R.drawable.galery1_1, "Накарміць галодных", Side.LEFT),
                GalleryImage(2, R.drawable.galery1_11, "Суцяшаць засмучаных", Side.RIGHT),
                GalleryImage(3, R.drawable.galery1_3, "адзець нагога", Side.LEFT),
                GalleryImage(4, R.drawable.galery1_5, "Адведаць вязня", Side.LEFT),
                GalleryImage(5, R.drawable.galery1_8, "Настаўляць грэшнікаў", Side.RIGHT),
                    GalleryImage(6, R.drawable.galery1_6, "Наведаць хворых", Side.LEFT),
                    GalleryImage(7, R.drawable.galery1_10, "Вучыць тых, хто не ведае", Side.RIGHT),
            ).reversed()// в обратном порядке, т.к. в контейнер картинки добавляются последовательно, и сверху будет лежать последняя добавленная
        )
}
