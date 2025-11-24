package com.carloclub.roadtoheaven.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.carloclub.roadtoheaven.R

class CityAdapter(
    private val items: List<MapCity>,
    private val listener: (MapCity) -> Unit
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

        fun bind(mapCity: MapCity) {
            val cityImageView: ImageView = itemView.findViewById(R.id.cityImageView)
            val lockImageView: ImageView = itemView.findViewById(R.id.lockImageView)

            cityImageView.setImageResource(mapCity.city.imageRes)
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
