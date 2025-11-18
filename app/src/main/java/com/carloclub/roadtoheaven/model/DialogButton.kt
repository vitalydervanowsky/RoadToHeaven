package com.carloclub.roadtoheaven.model

data class DialogButton(
    val title: String,
    val action: () -> Unit,
)
