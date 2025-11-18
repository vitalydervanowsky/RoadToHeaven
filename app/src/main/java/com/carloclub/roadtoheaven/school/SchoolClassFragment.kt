@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.school

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.City
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.DialogMessage.showMessage
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.DialogButton
import com.carloclub.roadtoheaven.model.DialogButtonListener
import com.carloclub.roadtoheaven.school.model.ClassLessonData
import com.carloclub.roadtoheaven.school.model.ClassType
import com.carloclub.roadtoheaven.story.StoryFragment
import com.carloclub.roadtoheaven.story.model.StoryData

class SchoolClassFragment : Fragment() {

    private var closeImageView: ImageView? = null
    private var titleTextView: TextView? = null

    private var isLessonDone = false

    private var classType: ClassType = ClassType.A
    private var classLessonData: ClassLessonData? = null

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
        val city = arguments?.getSerializable(Constants.CITY_ARG) as? City ?: City.SOKULKA
        classType = arguments?.getSerializable(CLASS_TYPE_ARG) as? ClassType ?: ClassType.A
        classLessonData = SchoolHelper.getLessonData(city, classType)
        isLessonDone = false
    }

    private fun initViews(view: View) {
        closeImageView = view.findViewById(R.id.closeImageView)
        titleTextView = view.findViewById(R.id.titleTextView)

        titleTextView?.text = classLessonData?.title
        closeImageView?.setOnClickListener { requireActivity().onBackPressed() }
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
        showMessage(
            0,
            0,
            classLessonData?.dialogMessage,
            null,
            requireActivity(),
            R.drawable.olga,
            DialogButton(
                title = "Хачу ведаць!",
                listener = object : DialogButtonListener {
                    override fun onButtonClicked() {
                        startLesson()
                    }
                }
            ),
            DialogButton(
                title = "Выйсцi з класа",
                listener = object : DialogButtonListener {
                    override fun onButtonClicked() {
                        requireActivity().onBackPressed()
                    }
                }
            ),
            null,
        )
    }

    private fun showCongratsDialog() {
        showMessage(
            0,
            0,
            "Малайчынка! Цяпер ты можаш паспрабаваць выканаць заданне па тэме i атрымаць камень",
            null,
            requireActivity(),
            R.drawable.olga,
            DialogButton(
                title = "Выканаць заданне",
                listener = object : DialogButtonListener {
                    override fun onButtonClicked() {
                        startPuzzle()
                    }
                }
            ),
            DialogButton(
                title = "Выйсцi з класа",
                listener = object : DialogButtonListener {
                    override fun onButtonClicked() {
                        requireActivity().onBackPressed()
                    }
                }
            ),
            null,
        )
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
        val fragment = StoryFragment.newInstance(
            StoryData(
                position = 0,
                pages = classLessonData?.pages.orEmpty()
            )
        )
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(StoryFragment::class.java.simpleName)
            .commit()
    }

    private fun startPuzzle() {
        requireActivity().setResult(RESULT_OK)
        requireActivity().finish()
    }

    companion object {
        private const val CLASS_TYPE_ARG = "CLASS_TYPE_ARG"

        fun newInstance(city: City?, classType: ClassType): SchoolClassFragment =
            SchoolClassFragment().apply {
                arguments = bundleOf(
                    Constants.CITY_ARG to city,
                    CLASS_TYPE_ARG to classType
                )
            }
    }
}
