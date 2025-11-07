@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.gallery

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.gallery.model.GalleryData

class GalleryActivity : AppCompatActivity() {

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
        val galleryData = if (intent.extras != null) {
            (intent.extras?.getSerializable(GalleryFragment.GALLERY_IMAGES_ARG) as? GalleryData?)
        } else {
            GalleryHelper.getGalleryData()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, GalleryFragment.newInstance(galleryData))
            .commit()
    }
}
