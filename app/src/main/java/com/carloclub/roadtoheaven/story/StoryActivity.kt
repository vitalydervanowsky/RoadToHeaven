@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.story

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.story.model.StoryData

class StoryActivity : AppCompatActivity() {

    private var storyData: StoryData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        if (intent.extras != null) {
            storyData = intent.extras?.getSerializable(StoryFragment.STORY_DATA_ARG) as? StoryData
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, StoryFragment.newInstance(storyData))
            .commit()
    }
}
