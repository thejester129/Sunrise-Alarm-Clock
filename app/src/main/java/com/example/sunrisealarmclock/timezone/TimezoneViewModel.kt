package com.example.sunrisealarmclock.timezone

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TimezoneViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var dao: TimezoneDAO
    lateinit var timezoneLocation: TimezoneLocation
        private set
    var timezones : Array<TimezoneLocation> = arrayOf()
        private set
    lateinit var liveTimezones: LiveData<Array<TimezoneLocation>>
    var dataLoading = MutableLiveData(true)

    init{
        viewModelScope.launch {
            dao = TimezoneDatabase.getInstance(application).dao()
            dao.getAllLocations()?.let { timezones = it }
            liveTimezones = dao.getAllLiveLocations()
            timezoneLocation = dao.getCurrentLocation()
            dataLoading.postValue(false)
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