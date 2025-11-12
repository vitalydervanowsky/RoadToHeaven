package com.carloclub.roadtoheaven.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.carloclub.roadtoheaven.DialogMessage.showMessage
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.DialogButton

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
        showMessage(
            0,
            0,
            "Текст поздравления или предупреждения или любого произвольного сообщения",
            null,
            requireActivity(),
            R.drawable.olga,
            null,
            DialogButton("NO") {
                requireActivity().finish()
            }
        )
    }

    private fun openClassA() {
        openClass(SchoolClassFragment.A_CLASS)
    }

    private fun openClassB() {
        openClass(SchoolClassFragment.B_CLASS)
    }

    private fun openClass(type: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, SchoolClassFragment.newInstance(type))
            .addToBackStack(SchoolClassFragment::class.java.simpleName)
            .commit()
    }
}
