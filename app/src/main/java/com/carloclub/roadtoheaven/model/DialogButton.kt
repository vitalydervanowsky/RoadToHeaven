package com.carloclub.roadtoheaven.model

data class DialogButton(
    val title: String,
    val listener: DialogButtonListener? = null,
)
