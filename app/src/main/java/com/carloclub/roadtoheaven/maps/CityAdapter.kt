package com.carloclub.roadtoheaven.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.carloclub.roadtoheaven.R
import com.carloclub.roadtoheaven.databases.Mission
import com.carloclub.roadtoheaven.databases.RthBase

class CityAdapter(
    private val items: List<Mission>,
    private val listener: (Mission) -> Unit
) : RecyclerView.Adapter<CityAdapter.MainCity>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCity {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_city, parent, false)
        return MainCity(itemView)
    }

    override fun onBindViewHolder(holder: MainCity, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class MainCity(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mapCity: Mission) {
            val cityImageView: ImageView = itemView.findViewById(R.id.cityImageView)
            val lockImageView: ImageView = itemView.findViewById(R.id.lockImageView)

            cityImageView.setImageBitmap(RthBase.instance.imageDao().getById(mapCity.imageId).getImage())
            if (mapCity.isEnabled) {
                cityImageView.alpha = 1f
                lockImageView.visibility = View.GONE
            } else {
                cityImageView.alpha = 0.2f
                lockImageView.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                if (mapCity.isEnabled) {
                    listener(mapCity)
                }
            }
        }
    }
}
