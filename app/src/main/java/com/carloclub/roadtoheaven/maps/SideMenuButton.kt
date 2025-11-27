package com.carloclub.roadtoheaven.maps

import com.carloclub.roadtoheaven.R

data class SideMenuButton(
    val id: Type,
) {
    enum class Type(val imageRes: Int) {
        SETTINGS(R.drawable.icon_setting),
        TASKS(R.drawable.icon_tasks),
        BAG(R.drawable.icon_bag),
        TOW(R.drawable.icon_tow),
    }
}
