@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.school

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.databases.Mission
import com.carloclub.roadtoheaven.databases.RthBase

class SchoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment_container)
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        val idm: Int = intent.getIntExtra(Constants.CITY_ARG,0)
        val mission: Mission = RthBase.instance.missionDao().getById(idm)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, SchoolHallFragment.newInstance(mission))
            .addToBackStack(SchoolHallFragment::class.java.simpleName)
            .commit()
    }
}
