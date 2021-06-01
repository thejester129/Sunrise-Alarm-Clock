package com.example.sunrisealarmclock.api

import androidx.lifecycle.MutableLiveData


interface SunriseTimeService {
    fun sunriseTime() : MutableLiveData<Long>
    suspend fun fetchLatestData():Long
}