package com.carloclub.roadtoheaven.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.carloclub.roadtoheaven.R

class SideMenuButtonAdapter(
    private val items: List<SideMenuButton>,
    private val listener: (SideMenuButton.Type) -> Unit
) : RecyclerView.Adapter<SideMenuButtonAdapter.Button>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Button {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_side_menu_button, parent, false)
        return Button(itemView)
    }

    override fun onBindViewHolder(holder: Button, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class Button(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(sideMenuButton: SideMenuButton) {
            itemView.findViewById<ImageView>(R.id.imageView).apply {
                setImageResource(sideMenuButton.id.imageRes)
                setOnClickListener {
                    listener(sideMenuButton.id)
                }
            }
        }
    }
}
