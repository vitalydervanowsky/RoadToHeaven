package com.carloclub.roadtoheaven.story

import android.app.Activity
import android.content.Intent
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.story.model.PageData
import com.carloclub.roadtoheaven.story.model.StoryData

object StoryHelper {
    fun showStoryActivity(activity: Activity) {
        activity.startActivity(Intent(activity, StoryActivity::class.java).apply {
            putExtra(StoryFragment.STORY_DATA_ARG, getStoryData())
        })
    }

    private fun getStoryData() =
        StoryData(
            position = 0,
            pages = listOf(
                PageData(
                    text = "R.drawable.galery1_1",
                    imageRes = R.drawable.galery1_1,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "R.drawable.galery1_2",
                    imageRes = R.drawable.galery1_2,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "R.drawable.galery1_3",
                    imageRes = R.drawable.galery1_3,
                    audioRes = R.raw.organ
                ),
                PageData(
                    text = "R.drawable.galery1_4",
                    imageRes = R.drawable.galery1_4,
                    audioRes = R.raw.organ
                )
            )
        )
}
