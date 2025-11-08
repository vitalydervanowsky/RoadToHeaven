@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.DialogMessage
import com.carloclub.roadtoheaven.R

class SchoolStartFragment : Fragment() {

//    private var textView: TextView? = null
//    private var imageView: ImageView? = null
//    private var backImageView: ImageView? = null
//    private var nextImageView: ImageView? = null
//    private var muteImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
//        storyData = arguments?.getSerializable(STORY_DATA_ARG) as? StoryData// get gata from DataGame
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        updateViews()
        DialogMessage.showMessage(
            0,
            0,
            "Текст поздравления или предупреждения или любого произвольного сообщения",
            null,
            requireActivity(),
            R.drawable.olga,
        ) {
            requireActivity().finish()
        }
    }

    private fun initViews(view: View) {
//        textView = view.findViewById(R.id.textView)
//        imageView = view.findViewById(R.id.imageView)
//        backImageView = view.findViewById(R.id.backImageView)
//        nextImageView = view.findViewById(R.id.nextImageView)
//        muteImageView = view.findViewById(R.id.muteImageView)

//        nextImageView?.setOnClickListener { goNext() }
//        backImageView?.setOnClickListener { goBack() }
//        muteImageView?.setOnClickListener { mute() }
    }

    private fun updateViews() {

    }
}
