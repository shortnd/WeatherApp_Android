package design.shortnd.weatherapp

import android.location.Geocoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

interface WeatherAPI {
    @GET("yql?format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys")
    fun getForecast(@Query("q") searchCity: String): Call<Weather>
}
/**
 * These are all the different "NODES" in the JSON file
 * */
// Holds the Query object from Yahoo API
class Weather(val query: WeatherQuery)
class WeatherQuery(val results: WeatherResults)
class WeatherResults(val channel: WeatherChannel)
class WeatherChannel(val location: WeatherLocation, val item: WeatherItem)
class WeatherLocation(val city: String, val region: String)
class WeatherItem(val forecast: List<Forecast>)
class Forecast(val date: String,
               val day: String,
               val high: String,
               val low: String,
               val text: String)

class WeatherRetriever {
    private val service: WeatherAPI

    init {
        // Gives us a retrofit object
        val retrofit = Retrofit.Builder()
                // Base URL for the Yahoo Weather API
                .baseUrl("https://query.yahooapis.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(WeatherAPI::class.java)
    }

    fun getForecast(callback: Callback<Weather>, searchT: String) {
        var searchTerm = searchT
            if (searchT == "") {
                searchTerm = "New York"
            }
        val citySearch = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"$searchTerm\")"
        val call = service.getForecast(citySearch)
        call.enqueue(callback)
    }
}