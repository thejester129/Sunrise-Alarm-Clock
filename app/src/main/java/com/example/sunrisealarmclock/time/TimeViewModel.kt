package com.example.sunrisealarmclock.time

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TimeViewModel(application: Application) : AndroidViewModel(application){
    private val dao = SunriseTimeDatabase.getInstance(application).dao()
    private val repo = TimeRepository(dao, application)
    var sunriseTime : MutableLiveData<Long> = MutableLiveData()
    var city : MutableLiveData<String> = MutableLiveData()

    init{
        viewModelScope.launch {
            repo.refreshSunriseInfo()
        }
        repo.latestSunriseTime.observeForever { latestTime ->
            sunriseTime.postValue(latestTime)
        }
    }
    fun nextSunriseTime() = sunriseTime


}