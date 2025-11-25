package com.carloclub.roadtoheaven.model

import com.carloclub.roadtoheaven.R

enum class Person(
    val smallSizeImageRes: Int,
    val withStoneImageRes: Int,
) {
    CARLO(
        smallSizeImageRes = R.drawable.carlo,
        withStoneImageRes = R.drawable.carlo,
    ),
    OLGA(
        smallSizeImageRes = R.drawable.olga,
        withStoneImageRes = R.drawable.persona6
    ),
//    KARALINA(
//        smallSizeImageRes = R.drawable.karalina,
//        withStoneImageRes = R.drawable.karalina,
//    ),
    KSENIYA(
        smallSizeImageRes = R.drawable.kseniya,
        withStoneImageRes = R.drawable.kseniya,
    ),
    FATHER_EUGENE(
        smallSizeImageRes = R.drawable.father_eugene,
        withStoneImageRes = R.drawable.father_eugene
    ),
}
