package com.example.sunrisealarmclock.api

import androidx.lifecycle.MutableLiveData
import com.example.sunrisealarmclock.timezone.TimezoneLocation


interface SunriseTimeService {
    var timezoneLocation: TimezoneLocation?
    fun sunriseTime() : MutableLiveData<Long>
    suspend fun fetchLatestData():Long
}