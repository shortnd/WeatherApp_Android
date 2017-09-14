package design.shortnd.weatherapp

import android.content.Intent
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchTerm = findViewById<EditText>(R.id.enter_your_city_edit_text)

        val searchWeatherButton = findViewById<Button>(R.id.search_city_button)
        searchWeatherButton.setOnClickListener {
            val goToWeatherListActivity = Intent(this, WeatherListActivity::class.java)
            goToWeatherListActivity.putExtra("searchTerm", searchTerm.text.toString())
            startActivity(goToWeatherListActivity)
        }
    }
}
