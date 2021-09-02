package com.example.sunrisealarmclock.api

import androidx.lifecycle.MutableLiveData


interface SunriseTimeService {
    var city: String?
    fun sunriseTime() : MutableLiveData<Long>
    suspend fun fetchLatestData():Long
}