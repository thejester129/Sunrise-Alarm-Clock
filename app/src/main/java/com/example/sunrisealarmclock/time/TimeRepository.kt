package com.example.sunrisealarmclock.time

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.sunrisealarmclock.api.SunriseTimeService
import com.example.sunrisealarmclock.api.WeatherApiService
import com.example.sunrisealarmclock.timezone.TimezoneLocation
import java.util.*

class TimeRepository (private val dao : SunriseTimeDAO, private val context: Context){
    private val sunriseTimeService: SunriseTimeService = WeatherApiService(context, null)
    val latestSunriseTime : MutableLiveData<Long> = MutableLiveData()
    private val timeoutPeriod : Int = 8 * 60 * 60 * 1000 // 8 hours
    private var timeLastFetched : Long = 0
    var city: TimezoneLocation? = null
        set(value){
            sunriseTimeService.timezoneLocation = value
            field = value
        }


    init{
        sunriseTimeService.sunriseTime().observeForever { time ->
            latestSunriseTime.postValue(time)
        }
    }

    suspend fun tryRefreshSunriseInfo(){
        if(isDataStale()){
            forceRefreshSunriseInfo()
        }
        else{
            latestSunriseTime.postValue(dao.get().time)
        }
    }

    suspend fun forceRefreshSunriseInfo(){
        val latestTime = sunriseTimeService.fetchLatestData()
        timeLastFetched = Calendar.getInstance().timeInMillis
        dao.save(SunriseTimeEntry(latestTime))
        latestSunriseTime.postValue(latestTime)
    }


    private fun isDataStale():Boolean{
        if(timeLastFetched == 0L){
            return true
        }
        return (Calendar.getInstance().timeInMillis - timeLastFetched) > timeoutPeriod
    }
}
