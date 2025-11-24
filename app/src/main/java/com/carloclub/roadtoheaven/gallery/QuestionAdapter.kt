package com.carloclub.roadtoheaven.gallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.model.GalleryImage
import com.carloclub.roadtoheaven.model.State

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.Question>() {

    private val items = mutableListOf<GalleryImage>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<GalleryImage>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Question =
        Question(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_question_bar, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Question, position: Int) {
        holder.bind(items[position], position)
    }

    inner class Question(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(galleryImage: GalleryImage, position: Int) {
            itemView.apply {
                findViewById<TextView>(R.id.numberTextView).apply {
                    val title = (position + 1).toString()
                    text = title
                    val color = if (galleryImage.state == State.CORRECT) {
                        R.color.green_correct
                    } else {
                        R.color.black
                    }
                    setTextColor(ContextCompat.getColor(context, color))
                }
            }
        }
    }
}
