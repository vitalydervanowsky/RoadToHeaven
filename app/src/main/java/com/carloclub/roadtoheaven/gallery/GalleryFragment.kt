@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.gallery

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.DialogMessage
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.gallery.model.GalleryData
import com.carloclub.roadtoheaven.gallery.model.GalleryImage
import com.carloclub.roadtoheaven.gallery.model.Side
import com.carloclub.roadtoheaven.gallery.model.State

class GalleryFragment : Fragment() {

    private var stackContainer: FrameLayout? = null
    private var leftDropArea: FrameLayout? = null
    private var rightDropArea: FrameLayout? = null
    private var questionRecyclerView: RecyclerView? = null
    private var titleTextView: TextView? = null
    private var pictureNameTextView: TextView? = null

    private var galleryData: GalleryData? = null
    private var questionAdapter: QuestionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViews(view)
        initListeners()
        initAdapter()
        setDataToViews()
    }

    private fun initData() {
        galleryData = arguments?.getSerializable(GALLERY_IMAGES_ARG) as? GalleryData
    }

    private fun initViews(view: View) {
        stackContainer = view.findViewById(R.id.stackContainer)
        leftDropArea = view.findViewById(R.id.leftDropArea)
        rightDropArea = view.findViewById(R.id.rightDropArea)
        questionRecyclerView = view.findViewById(R.id.questionRecyclerView)
        titleTextView = view.findViewById(R.id.titleTextView)
        pictureNameTextView = view.findViewById(R.id.pictureNameTextView)
    }

    private fun initListeners() {
        // Обработчик дропа для боковых зон
        val dragListener = View.OnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.alpha = 0.7f
                    true
                }

                DragEvent.ACTION_DRAG_EXITED -> {
                    v.alpha = 1.0f
                    true
                }

                DragEvent.ACTION_DROP -> {
                    v.alpha = 1.0f
                    val side = if (v.id == R.id.leftDropArea) Side.LEFT else Side.RIGHT
                    // Получаем данные из drag
                    val id = event.clipData.getItemAt(0).text.toString().toInt()
                    val galleryImage = galleryData?.images?.find { it.id == id }
                    val isCorrectAnswer = side == galleryImage?.correctSide
                    if (isCorrectAnswer) {
                        galleryImage?.state = State.CORRECT
                        updateItems(questionAdapter)
                        stackContainer?.children?.find { it.tag == id }?.visibility = View.GONE
                        checkIsFinished()
                    }
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    v.alpha = 1.0f
                    true
                }

                else -> false
            }
        }

        leftDropArea?.setOnDragListener(dragListener)
        rightDropArea?.setOnDragListener(dragListener)
    }

    private fun initAdapter() {
        questionAdapter = QuestionAdapter()
        questionRecyclerView?.adapter = questionAdapter
        updateItems(questionAdapter)
    }

    private fun setDataToViews() {
        titleTextView?.text = galleryData?.title
        galleryData?.images?.forEach { galleryImage ->
            val imageView = ImageView(requireContext()).apply {
                tag = galleryImage.id
                setImageResource(galleryImage.imageRes)
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                // Начало drag по долгому нажатию
                setOnLongClickListener { v ->
                    val item = ClipData.Item(galleryImage.id.toString())
                    val dragData = ClipData(
                        "image_id",
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        item
                    )
                    val shadow = View.DragShadowBuilder(v)
                    v.startDragAndDrop(dragData, shadow, v, 0)
                    true
                }
            }
            stackContainer?.addView(imageView)
        }

        galleryData?.images?.last()?.let { showTitle(it) }
    }

    private fun updateItems(adapter: QuestionAdapter?) {
        galleryData?.images?.reversed()?.let { adapter?.updateItems(it) }
    }

    private fun showTitle(galleryImage: GalleryImage) {
        pictureNameTextView?.text = galleryImage.title
    }

    private fun hideTitle() {
        pictureNameTextView?.visibility = View.INVISIBLE
    }

    private fun checkIsFinished() {
        if (galleryData?.images?.none { it.state == State.DEFAULT } == true) {
            hideTitle()
            Constants.DATAGAME.stones++
            DialogMessage.showMessage(
                R.drawable.gratulation,
                R.drawable.stones1,
                "Поздравляем! Вы добыли 1 камень",
                "Собрано: " + Constants.DATAGAME.stones.toString(),
                requireActivity()
            )
        } else {
            galleryData?.images?.reversed()?.firstOrNull { it.state == State.DEFAULT }?.let {
                showTitle(it)
            } ?: run {
                hideTitle()
            }
        }
    }

    companion object {
        const val GALLERY_IMAGES_ARG = "GALLERY_IMAGES_ARG"

        @JvmStatic
        fun newInstance(galleryData: GalleryData?): GalleryFragment =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(GALLERY_IMAGES_ARG, galleryData)
                }
            }
    }
}
