@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.story

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.story.model.StoryData

class StoryFragment : Fragment() {

    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var backImageView: ImageView? = null
    private var nextImageView: ImageView? = null
    private var muteImageView: ImageView? = null

    private var storyData: StoryData? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isMuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        storyData = arguments?.getSerializable(STORY_DATA_ARG) as? StoryData
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        updateViews()
    }

    private fun initViews(view: View) {
        textView = view.findViewById(R.id.textView)
        imageView = view.findViewById(R.id.imageView)
        backImageView = view.findViewById(R.id.backImageView)
        nextImageView = view.findViewById(R.id.nextImageView)
        muteImageView = view.findViewById(R.id.muteImageView)

        nextImageView?.setOnClickListener { goNext() }
        backImageView?.setOnClickListener { goBack() }
        muteImageView?.setOnClickListener { mute() }
        mute()
    }

    private fun updateViews() {
        storyData?.position?.let {
            storyData?.pages?.get(it)?.let { pageData ->
                textView?.text = pageData.text
                imageView?.setImageResource(pageData.imageRes)
                mediaPlayer?.stop()
                mediaPlayer = MediaPlayer.create(activity, pageData.audioRes)
                if (isMuted) {
                    muteImageView?.setImageResource(android.R.drawable.ic_media_play)
                } else {
                    mediaPlayer?.start()
                    muteImageView?.setImageResource(android.R.drawable.ic_media_pause)
                }
            }
        }
        backImageView?.setImageResource(
            if (storyData?.position == 0) {
                android.R.drawable.ic_menu_close_clear_cancel
            } else {
                android.R.drawable.ic_media_rew
            }
        )
        nextImageView?.setImageResource(
            if (((storyData?.position ?: 0) + 1) == storyData?.pages?.size) {
                android.R.drawable.ic_menu_close_clear_cancel
            } else {
                android.R.drawable.ic_media_ff
            }
        )
    }

    private fun goBack() {
        if (storyData?.position == 0) {
            requireActivity().finish()
            return
        } else {
            storyData?.let {
                it.position--
                updateViews()
            }
        }
    }

    private fun goNext() {
        if (((storyData?.position ?: 0) + 1) == storyData?.pages?.size) {
            requireActivity().finish()
            return
        } else {
            storyData?.let {
                it.position++
                updateViews()
            }
        }
    }

    private fun mute() {
        isMuted = !isMuted
        if (isMuted) {
            mediaPlayer?.pause()
            muteImageView?.setImageResource(android.R.drawable.ic_media_play)
        } else {
            mediaPlayer?.start()
            muteImageView?.setImageResource(android.R.drawable.ic_media_pause)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.stop()
    }

    companion object {
        const val STORY_DATA_ARG = "STORY_DATA_ARG"

        @JvmStatic
        fun newInstance(storyData: StoryData?): StoryFragment =
            StoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STORY_DATA_ARG, storyData)
                }
            }
    }
}
