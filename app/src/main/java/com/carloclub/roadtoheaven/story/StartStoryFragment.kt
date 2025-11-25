@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.story

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.MapObjects.MapObjectSchool
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.helper.MessageUtil.showDialog
import com.carloclub.roadtoheaven.model.ClassType
import com.carloclub.roadtoheaven.model.DialogButton
import com.carloclub.roadtoheaven.model.DialogButtonListener
import com.carloclub.roadtoheaven.model.StoryData

class StartStoryFragment : Fragment() {

    private var imageBack: ImageView? = null
    private var closeImageView: ImageView? = null
    private var titleTextView: TextView? = null

    private var isLessonDone = false

    private var classType: ClassType = ClassType.A
    private var storyData: StoryData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school_class, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViews(view)
        initPersonaDialogs()
        initFragmentResultListener()
    }

    private fun initData() {
        storyData = arguments?.getSerializable(StoryFragment.STORY_DATA_ARG) as? StoryData
        classType = arguments?.getSerializable(CLASS_TYPE_ARG) as? ClassType ?: ClassType.A
        isLessonDone = false
    }

    private fun initViews(view: View) {
        imageBack = view.findViewById(R.id.imageBack)
        closeImageView = view.findViewById(R.id.closeImageView)
        titleTextView = view.findViewById(R.id.titleTextView)

        storyData?.backgroundImageRes?.let { imageBack?.setImageResource(it) }
        titleTextView?.text = storyData?.title
        closeImageView?.setOnClickListener { goBack() }
    }

    private fun initPersonaDialogs() {
        view?.postDelayed({
            if (isLessonDone) {
                isLessonDone = false
                showCongratsDialog()
            } else {
                showStartDialog()
            }
        }, 50)
    }

    private fun showStartDialog() {
        storyData?.startDialogInfo?.let {
            showDialog(
                message = it.message,
                activity = requireActivity(),
                personImageRes = storyData?.person?.smallSizeImageRes,
                yesDialogButton = DialogButton(
                    title = it.yesButton,
                    listener = object : DialogButtonListener {
                        override fun onButtonClicked() {
                            startLesson()
                        }
                    }
                ),
                noDialogButton = DialogButton(
                    title = it.noButton,
                    listener = object : DialogButtonListener {
                        override fun onButtonClicked() {
                            goBack()
                        }
                    }
                ),
            )
        }
    }

    private fun showCongratsDialog() {
        if (Constants.DATAGAME.map.currentObject is MapObjectSchool) {
            val taskA = (Constants.DATAGAME.map.currentObject as MapObjectSchool).task
            val taskB = (Constants.DATAGAME.map.currentObject as MapObjectSchool).taskB
            if (classType == ClassType.A) {
                if (taskA.isFinished) {
                    goBack()
                    return
                }
                taskA.isStarted = true
                taskB.isStarted = false
            }
            if (classType == ClassType.B) {
                if (taskB.isFinished) {
                    goBack()
                    return
                }
                taskA.isStarted = false
                taskB.isStarted = true
            }
        }

        storyData?.endDialogInfo?.let {
            showDialog(
                message = it.message,
                activity = requireActivity(),
                personImageRes = storyData?.person?.smallSizeImageRes,
                yesDialogButton = DialogButton(
                    title = it.yesButton,
                    listener = object : DialogButtonListener {
                        override fun onButtonClicked() {
                            startPuzzle()
                        }
                    }
                ),
                noDialogButton = DialogButton(
                    title = it.noButton,
                    listener = object : DialogButtonListener {
                        override fun onButtonClicked() {
                            goBack()
                        }
                    }
                ),
            )
        }
    }

    private fun initFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            StoryFragment.STORY_RESULT_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            isLessonDone = bundle.getBoolean(StoryFragment.IS_DONE, false)
        }
    }

    private fun startLesson() {
        val fragment = StoryFragment.newInstance(storyData)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(StoryFragment::class.java.simpleName)
            .commit()
    }

    private fun startPuzzle() {
        requireActivity().setResult(RESULT_OK)
        requireActivity().finish()
    }

    private fun goBack() {
        requireActivity().onBackPressed()
    }

    companion object {
        private const val CLASS_TYPE_ARG = "CLASS_TYPE_ARG"

        fun newInstance(storyData: StoryData?, classType: ClassType? = null): StartStoryFragment =
            StartStoryFragment().apply {
                arguments = bundleOf(
                    StoryFragment.STORY_DATA_ARG to storyData,
                    CLASS_TYPE_ARG to classType
                )
            }
    }
}
