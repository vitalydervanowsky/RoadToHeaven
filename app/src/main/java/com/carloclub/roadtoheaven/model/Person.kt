package com.carloclub.roadtoheaven.model

import com.carloclub.roadtoheaven.R

enum class Person(
    val fullSizeImageRes: Int = 0,
    val smallSizeImageRes: Int,
    val withStoneImageRes: Int = 0,
) {
    CARLO(smallSizeImageRes = R.drawable.carlo),
    OLGA(smallSizeImageRes = R.drawable.olga),
    KARALINA(smallSizeImageRes = R.drawable.karalina),
    KSENIYA(smallSizeImageRes = R.drawable.kseniya),
}
