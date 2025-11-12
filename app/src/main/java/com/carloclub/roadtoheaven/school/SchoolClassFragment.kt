@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.school

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.DialogMessage.showMessage
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.DialogButton
import com.carloclub.roadtoheaven.school.model.ClassLessonData
import com.carloclub.roadtoheaven.story.StoryFragment
import com.carloclub.roadtoheaven.story.model.StoryData

class SchoolClassFragment : Fragment() {

    private var closeImageView: ImageView? = null
    private var titleTextView: TextView? = null

    private var isLessonDone = false

    private lateinit var classLessonData: ClassLessonData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        classLessonData = if (arguments?.getString(CLASS_TYPE_ARG) == A_CLASS) {
            SchoolHelper.getAClassLessonData()
        } else {
            SchoolHelper.getBClassLessonData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school_class, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLessonDone = false
        initViews(view)
        initPersonaDialogs()
        initFragmentResultListener()
    }

    private fun initViews(view: View) {
        closeImageView = view.findViewById(R.id.closeImageView)
        titleTextView = view.findViewById(R.id.titleTextView)

        titleTextView?.text = classLessonData.title
        closeImageView?.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initPersonaDialogs() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isLessonDone) {
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
            "Сення на занятку мы пазнаемiмся з учынкамi мiласэрнасцi для душы",
            null,
            requireActivity(),
            R.drawable.olga,
            DialogButton("Хачу ведаць!") {
                startLesson()
            },
            DialogButton("Выйсцi з класа") {
                requireActivity().onBackPressed()
            }
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
            DialogButton("Выканаць заданне") {
                // todo open puzzle here
            },
            DialogButton("Выйсцi з класа") {
                requireActivity().onBackPressed()
            }
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
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainerView,
                StoryFragment.newInstance(
                    StoryData(
                        position = 0,
                        pages = classLessonData.pages
                    )
                )
            )
            .addToBackStack(StoryFragment::class.java.simpleName)
            .commit()
    }

    companion object {
        const val A_CLASS = "A_CLASS"
        const val B_CLASS = "B_CLASS"
        private const val CLASS_TYPE_ARG = "CLASS_TYPE_ARG"

        fun newInstance(classType: String): SchoolClassFragment =
            SchoolClassFragment().apply {
                arguments = bundleOf(
                    CLASS_TYPE_ARG to classType
                )
            }
    }
}
