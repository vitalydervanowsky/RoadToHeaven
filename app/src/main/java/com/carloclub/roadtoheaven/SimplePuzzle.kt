package com.carloclub.roadtoheaven

import android.app.Dialog
import android.widget.ImageView
import com.carloclub.roadtoheaven.MapObjects.MapObject
import com.carloclub.roadtoheaven.MyMap.Question

class SimplePuzzle(
    mapObject: MapObject,
    question: Question
) : Puzzle(mapObject, question) {

    init {
        this.mapObject = mapObject
        this.question = question
        dialog = Dialog(mapObject.mapActivity, R.style.FullScreenDialog)
        dialog.setContentView(R.layout.dialog_simple_puzzle)

        dialog.findViewById<ImageView>(R.id.closeImageView).setOnClickListener { dialog.dismiss() }

        victorina = Victorina(mapObject, dialog)
        victorina.loadQuestion(question)
        victorina.showAnswers()
    }
}
