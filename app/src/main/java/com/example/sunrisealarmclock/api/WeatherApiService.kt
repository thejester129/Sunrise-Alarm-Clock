package com.example.sunrisealarmclock.api

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.URL

class WeatherApiService(private val context: Context, override var city: String?) : SunriseTimeService {
    private val sunriseTime : MutableLiveData<Long> = MutableLiveData()

    override fun sunriseTime(): MutableLiveData<Long> {
        return sunriseTime
    }

    private fun getWeatherUrl(): String?{
        if(city!= null){
            return "https://api.openweathermap.org/data/2.5/weather?q=%s,uk&appid=a5a753c0eab15f70b1c44d1b313bb610".format(city)
        }
        return null
    }

    override suspend fun fetchLatestData(): Long {
        val url: String = getWeatherUrl() ?: return 0
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val sunrise = response.getJSONObject("sys").getLong("sunrise")
                val sunriseFormatted = sunrise * 1000
                sunriseTime.postValue(sunriseFormatted)
            },
            { error ->
                // TODO: Handle error
            }
        )

        queue.add(jsonObjectRequest)
        return 0
    }
}