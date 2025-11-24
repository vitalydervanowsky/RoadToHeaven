package com.carloclub.roadtoheaven.helper

import com.carloclub.roadtoheaven.maps.City
import com.carloclub.roadtoheaven.maps.MapCity

object CityHelper {

    fun getAllCities(): List<MapCity> =
        listOf(
            MapCity(
                city = City.SOKOLKA,
                isEnabled = true
            ),
            MapCity(
                city = City.AUGSBURG,
                isEnabled = false
            ),
            MapCity(
                city = City.LIEGE,
                isEnabled = false
            ),
            MapCity(
                city = City.FAVERNEY,
                isEnabled = false
            ),
            MapCity(
                city = City.LANCIANO,
                isEnabled = false
            ),
                MapCity(
                        city = City.BUENOS,
                        isEnabled = false
                ),
                MapCity(
                        city = City.TUMAKA,
                        isEnabled = false
                ),
                MapCity(
                        city = City.TRYWANDRUM,
                        isEnabled = false
                ),
                MapCity(
                        city = City.LIPA,
                        isEnabled = false
                ),
        )
}
