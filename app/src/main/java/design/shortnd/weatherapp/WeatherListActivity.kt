package design.shortnd.weatherapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

class WeatherListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_list)
        title = ""

        val searchTerm = intent.extras.getString("searchTerm")
        print(searchTerm)

        // This is for Retrofit
        val retriver = WeatherRetriever()

        val callback = object : Callback<Weather> {

            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                title = "${response?.body()?.query?.results?.channel?.location?.city}, ${response?.body()?.query?.results?.channel?.location?.region}"
                println(response?.body()?.query?.results?.channel?.item?.forecast)

                val weatherListView = findViewById<ListView?>(R.id.weather_list_view)

                val forecasts = response?.body()?.query?.results?.channel?.item?.forecast

                val forecastStrings = mutableListOf<String>()

                forecasts?.mapTo(forecastStrings) {
                    "${it.day} - ${it.date} High: ${it.high}, Low: ${it.low}"
                }

                val adapter = ArrayAdapter(this@WeatherListActivity,
                        android.R.layout.simple_list_item_1, forecastStrings)
                if (weatherListView != null) {
                    weatherListView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                println("Its failed")
            }
        }
        retriver.getForecast(callback, searchTerm)
    }
}
