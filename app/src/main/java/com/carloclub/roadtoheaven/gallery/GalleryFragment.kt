@file:Suppress("DEPRECATION")

package com.carloclub.roadtoheaven.gallery

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.carloclub.roadtoheaven.Constants
import com.carloclub.roadtoheaven.DialogMessage
import com.carloclub.roadtoheaven.Messages
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.GalleryData
import com.carloclub.roadtoheaven.model.GalleryImage
import com.carloclub.roadtoheaven.model.State
import com.carloclub.roadtoheaven.model.ClassType
import kotlin.math.roundToInt

class GalleryFragment : Fragment() {

    private var frameImageView: ImageView? = null
    private var leftDropArea: FrameLayout? = null
    private var leftTextView: TextView? = null
    private var rightDropArea: FrameLayout? = null
    private var rightTextView: TextView? = null
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
        initAdapter()
        setDataToViews()
    }

    private fun initData() {
        galleryData = arguments?.getSerializable(GALLERY_IMAGES_ARG) as? GalleryData
    }

    private fun initViews(view: View) {
        frameImageView = view.findViewById(R.id.frameImageView)
        leftDropArea = view.findViewById(R.id.leftDropArea)
        leftTextView = view.findViewById(R.id.leftTextView)
        rightDropArea = view.findViewById(R.id.rightDropArea)
        rightTextView = view.findViewById(R.id.rightTextView)
        questionRecyclerView = view.findViewById(R.id.questionRecyclerView)
        titleTextView = view.findViewById(R.id.titleTextView)
        pictureNameTextView = view.findViewById(R.id.pictureNameTextView)
    }

    private fun initAdapter() {
        questionAdapter = QuestionAdapter()
        questionRecyclerView?.adapter = questionAdapter
    }

    private fun setDataToViews() {
        titleTextView?.text = galleryData?.title
        leftTextView?.text = galleryData?.topics?.get(ClassType.A)
        rightTextView?.text = galleryData?.topics?.get(ClassType.B)
        galleryData?.images?.forEach { galleryImage ->
            val imageView = createDraggableImageView(galleryImage)
            val rootView = view as? ConstraintLayout ?: return@forEach

            frameImageView?.let { frameImageView ->
                frameImageView.post {
                    imageView.id = View.generateViewId()
                    rootView.addView(imageView)

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(rootView)

                    val margin = dpToPx(FRAME_MARGIN)
                    // Привязываем к сторонам frameImageView
                    constraintSet.connect(
                        imageView.id, ConstraintSet.START,
                        frameImageView.id, ConstraintSet.START, margin
                    )
                    constraintSet.connect(
                        imageView.id, ConstraintSet.END,
                        frameImageView.id, ConstraintSet.END, margin
                    )
                    constraintSet.connect(
                        imageView.id, ConstraintSet.TOP,
                        frameImageView.id, ConstraintSet.TOP, margin
                    )
                    constraintSet.connect(
                        imageView.id, ConstraintSet.BOTTOM,
                        frameImageView.id, ConstraintSet.BOTTOM, margin
                    )

                    constraintSet.constrainWidth(imageView.id, 0)  // 0 = MATCH_CONSTRAINT
                    constraintSet.constrainHeight(imageView.id, 0) // 0 = MATCH_CONSTRAINT

                    constraintSet.applyTo(rootView)

                    showTitle(galleryImage)
                    updateQuestionNumberBar()
                }
            }
        }
    }

    private fun updateQuestionNumberBar() {
        galleryData?.images?.reversed()?.let { questionAdapter?.updateItems(it) }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createDraggableImageView(galleryImage: GalleryImage): ImageView {
        return ImageView(requireContext()).apply {
            tag = galleryImage.id
            setImageResource(galleryImage.imageRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
            adjustViewBounds = false

            elevation = 12f
            alpha = 0.98f // избежать прозрачности от родителя

            setOnTouchListener { v, event ->
                val parent = v.parent as? ViewGroup ?: return@setOnTouchListener false

                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        parent.requestDisallowInterceptTouchEvent(true)
                        // Поднимаем НАД всеми вью в том же родителе
                        v.bringToFront()
                        // Принудительно перерисовываем родителя
                        parent.invalidate()
                        true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val parentLocation = IntArray(2)
                        parent.getLocationOnScreen(parentLocation)

                        val x = event.rawX - parentLocation[0] - v.width / 2f
                        val y = event.rawY - parentLocation[1] - v.height / 2f

                        v.x = x.coerceIn(0f, parent.width - v.width.toFloat())
                        v.y = y.coerceIn(0f, parent.height - v.height.toFloat())

                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        val rawX = event.rawX
                        val rawY = event.rawY

                        val leftRect = IntArray(2).apply { leftDropArea?.getLocationOnScreen(this) ?: intArrayOf(0, 0) }
                        val rightRect = IntArray(2).apply { rightDropArea?.getLocationOnScreen(this) ?: intArrayOf(0, 0) }

                        val inLeftArea = leftDropArea != null &&
                                rawX >= leftRect[0] && rawX <= leftRect[0] + leftDropArea.getIntWidth() &&
                                rawY >= leftRect[1] && rawY <= leftRect[1] + leftDropArea.getIntHeight()

                        val inRightArea = rightDropArea != null &&
                                rawX >= rightRect[0] && rawX <= rightRect[0] + rightDropArea.getIntWidth() &&
                                rawY >= rightRect[1] && rawY <= rightRect[1] + rightDropArea.getIntHeight()

                        val classType = when {
                            inLeftArea -> ClassType.A
                            inRightArea -> ClassType.B
                            else -> null
                        }

                        val isCorrectAnswer = classType != null && classType == galleryImage.classType

                        if (isCorrectAnswer) {
                            galleryImage.state = State.CORRECT
                            updateQuestionNumberBar()
                            v.visibility = View.GONE
                            checkIsFinished()
                        } else {
                            val x = (frameImageView?.x ?: 0f) + dpToPx(FRAME_MARGIN)
                            val y = (frameImageView?.y ?: 0f) + dpToPx(FRAME_MARGIN)
                            v.animate()
                                .x(x)
                                .y(y)
                                .setDuration(300)
                                .start()
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showTitle(galleryImage: GalleryImage) {
        pictureNameTextView?.text = galleryImage.title
        pictureNameTextView?.visibility = View.VISIBLE
    }

    private fun hideTitle() {
        pictureNameTextView?.visibility = View.INVISIBLE
    }

    private fun checkIsFinished() {
        if (galleryData?.images?.all { it.state == State.CORRECT } == true) {
            frameImageView?.visibility = View.GONE
            hideTitle()
            Constants.DATAGAME.stones++
            DialogMessage.showMessage(
                R.drawable.gratulation,
                R.drawable.stones1,
                Messages.getMessageGotStone(),
                Messages.getMessageHowManyStonesGot() + Constants.DATAGAME.stones.toString(),
                requireActivity()
            )
        } else {
            galleryData?.images?.lastOrNull { it.state == State.DEFAULT }?.let {
                showTitle(it)
            } ?: hideTitle()
        }
    }

    private fun View?.getIntWidth(): Int = this?.width ?: 0

    private fun View?.getIntHeight(): Int = this?.height ?: 0

    private fun dpToPx(dp: Int): Int {
        val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.roundToInt()
    }

    companion object {
        private const val FRAME_MARGIN = 8
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