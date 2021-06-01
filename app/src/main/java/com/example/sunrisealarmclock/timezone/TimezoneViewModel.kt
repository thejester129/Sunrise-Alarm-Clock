package com.example.sunrisealarmclock.timezone

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TimezoneViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var dao : TimezoneDAO
    var timezoneLocation:TimezoneLocation = TimezoneLocation("London",0,50.0,50.0,false)
        private set
    var timezones : Array<TimezoneLocation> = emptyArray()
        private set

    init{
        viewModelScope.launch {
            dao = TimezoneDatabase.getInstance(application).dao()
            dao.getCurrentLocation()?.let { timezoneLocation = it }
            dao.getAllLocations()?.let { timezones = it }
        }
    }

    fun setTimezone(timezoneLocation: TimezoneLocation){
        this.timezoneLocation = timezoneLocation
        viewModelScope.launch {
            dao.resetCurrentLocation()
            dao.setAsCurrentLocation(timezoneLocation.city)
        }
    }
}