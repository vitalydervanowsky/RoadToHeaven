package com.carloclub.roadtoheaven.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.helper.LessonHelper
import com.carloclub.roadtoheaven.helper.MessageUtil.showDialog
import com.carloclub.roadtoheaven.model.DialogButton
import com.carloclub.roadtoheaven.model.DialogButtonListener
import com.carloclub.roadtoheaven.model.ClassType
import com.carloclub.roadtoheaven.model.Person
import com.carloclub.roadtoheaven.story.StartStoryFragment

class SchoolHallFragment : Fragment() {

    private var closeImageView: ImageView? = null
    private var classAButton: Button? = null
    private var classBButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school_hall, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initPersonaDialogs()
    }

    private fun initViews(view: View) {
        closeImageView = view.findViewById(R.id.closeImageView)
        classAButton = view.findViewById(R.id.classAButton)
        classBButton = view.findViewById(R.id.classBButton)

        closeImageView?.setOnClickListener { requireActivity().finish() }
        classAButton?.setOnClickListener { openClassA() }
        classBButton?.setOnClickListener { openClassB() }
    }

    private fun initPersonaDialogs() {
        showDialog(
            message = "Хвала Хрысту! Запрашаем на заняткi",
            activity = requireActivity(),
            personImageRes = Person.OLGA.smallSizeImageRes,
            yesDialogButton = DialogButton(
                title = "Добра",
                listener = null
            ),
            noDialogButton = DialogButton(
                title = "Выйсцi",
                listener = object : DialogButtonListener {
                    override fun onButtonClicked() {
                        requireActivity().finish()
                    }
                }
            ),
        )
    }

    private fun openClassA() {
        openClass(ClassType.A)
    }

    private fun openClassB() {
        openClass(ClassType.B)
    }

    private fun openClass(type: ClassType) {
        val city = arguments?.getSerializable(Constants.CITY_ARG) as? City
        val storyData = LessonHelper.getStoryDataForSchool(city, type)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, StartStoryFragment.newInstance(storyData, type))
            .addToBackStack(StartStoryFragment::class.java.simpleName)
            .commit()
    }

    companion object {
        fun newInstance(city: City): SchoolHallFragment =
            SchoolHallFragment().apply {
                arguments = bundleOf(
                    Constants.CITY_ARG to city
                )
            }
    }
}
