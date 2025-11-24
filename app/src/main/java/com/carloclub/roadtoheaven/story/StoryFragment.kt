@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.story

import android.media.MediaPlayer
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.StoryData

class StoryFragment : Fragment() {

    private var textView: TextView? = null
    private var imageView: ImageView? = null
    private var prevImageView: ImageView? = null
    private var nextImageView: ImageView? = null
    private var muteImageView: ImageView? = null
    private var closeImageView: ImageView? = null
    private var thankButton: Button? = null

    private var storyData: StoryData? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isMuted = true

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
        prevImageView = view.findViewById(R.id.prevImageView)
        nextImageView = view.findViewById(R.id.nextImageView)
        muteImageView = view.findViewById(R.id.muteImageView)
        closeImageView = view.findViewById(R.id.closeImageView)
        thankButton = view.findViewById(R.id.thankButton)

        prevImageView?.setImageResource(R.drawable.ic_media_prev)
        nextImageView?.setImageResource(R.drawable.ic_media_next)

        nextImageView?.setOnClickListener { goNext() }
        prevImageView?.setOnClickListener { goBack() }
        muteImageView?.setOnClickListener { mute() }
        closeImageView?.setOnClickListener { closeStory() }
        thankButton?.setOnClickListener { closeStory() }
        mute()
    }

    private fun updateViews() {
        storyData?.position?.let { position ->
            storyData?.pages?.get(position)?.let { pageData ->
                thankButton?.visibility = getButtonVisibility(!isLastPage())
                textView?.text = pageData.text
                pageData.imageRes?.let { imageView?.setImageResource(it) }
                mediaPlayer?.stop()
                mediaPlayer = pageData.audioRes?.let { MediaPlayer.create(activity, it) }
                if (isMuted) {
                    muteImageView?.setImageResource(R.drawable.ic_sound_off)
                } else {
                    mediaPlayer?.start()
                    muteImageView?.setImageResource(R.drawable.ic_sound_on)
                }
            }
        }
        prevImageView?.visibility = getButtonVisibility(storyData?.position == 0)
        nextImageView?.visibility = getButtonVisibility(isLastPage())
    }

    private fun isLastPage(): Boolean =
        storyData?.isLastPage() ?: false

    private fun getButtonVisibility(isInvisible: Boolean) =
        if (isInvisible) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

    private fun goBack() {
        storyData?.goBack()
        updateViews()
    }

    private fun goNext() {
        storyData?.goNext()
        updateViews()
    }

    private fun mute() {
        isMuted = !isMuted
        if (isMuted) {
            mediaPlayer?.pause()
            muteImageView?.setImageResource(R.drawable.ic_sound_off)
        } else {
            mediaPlayer?.start()
            muteImageView?.setImageResource(R.drawable.ic_sound_on)
        }
    }

    private fun closeStory() {
        // условие успешного прохождения урока - последний слайд
        if (isLastPage()) {
            setFragmentResult(
                STORY_RESULT_REQUEST_KEY,
                bundleOf(
                    IS_DONE to true
                )
            )
        }
        requireActivity().onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        setOnBackPressedListener()
    }

    private fun setOnBackPressedListener() {
        val view = requireView()
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _: View?, keyCode: Int, event: KeyEvent ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                closeStory()
                return@setOnKeyListener true
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.stop()
    }

    companion object {
        const val STORY_DATA_ARG = "STORY_DATA_ARG"
        const val STORY_RESULT_REQUEST_KEY = "STORY_RESULT_REQUEST_KEY"
        const val IS_DONE = "IS_DONE"

        @JvmStatic
        fun newInstance(storyData: StoryData?): StoryFragment =
            StoryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(STORY_DATA_ARG, storyData)
                }
            }
    }
}
