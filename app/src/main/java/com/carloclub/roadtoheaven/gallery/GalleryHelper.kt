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
            title = "У які зал змесціш гэтую карціну?",
            leftSideDescription = "Учынкі міласэрнасці для цела бліжняга",
            rightSideDescription = "Учынкі міласэрнасці для душы бліжняга",
            images = getGalleryImagesForSokulka().getRandomGalleryImages(),
        )

    // todo fix images
    private fun getGalleryImagesForSokulka(): List<GalleryImage> =
        listOf(
            GalleryImage(1, R.drawable.mersyacts8, "Настаўляць грэшнікаў", Side.RIGHT),
            GalleryImage(2, R.drawable.mersyacts10, "Вучыць тых, хто не ведае", Side.RIGHT),
            GalleryImage(3, R.drawable.mersyacts13, "Раіць тым, хто сумняваецца", Side.RIGHT),
            GalleryImage(4, R.drawable.mersyacts5, "Суцяшаць засмучаных", Side.RIGHT),
            GalleryImage(5, R.drawable.mersyacts12, "Зносіць цярпліва знявагу", Side.RIGHT),
            GalleryImage(6, R.drawable.mersyacts4, "Дараваць ахвотна крыўду", Side.RIGHT),
            GalleryImage(7, R.drawable.mersyacts11, "Маліцца за жывых і памерлых", Side.RIGHT),

            GalleryImage(8, R.drawable.mersyacts3, "Накарміць галодных", Side.LEFT),
            GalleryImage(9, R.drawable.mersyacts7, "Напаіць сасмаглых", Side.LEFT),
            GalleryImage(10, R.drawable.mersyacts14, "Адзець нагога", Side.LEFT),
            GalleryImage(11, R.drawable.mersyacts6, "Прыняць у дом падарожных", Side.LEFT),
            GalleryImage(12, R.drawable.mersyacts2, "Наведаць хворых", Side.LEFT),
            GalleryImage(13, R.drawable.mersyacts1, "Суцяшаць вязняў", Side.LEFT),
            GalleryImage(14, R.drawable.mersyacts9, "Пахаваць памерлых", Side.LEFT),
        )

    private fun List<GalleryImage>.getRandomGalleryImages(n: Int = 7): List<GalleryImage> {
        if (n <= 0) return emptyList()
        if (n >= size) return this.shuffled()
        return shuffled().take(n.coerceAtMost(size))
    }
}
