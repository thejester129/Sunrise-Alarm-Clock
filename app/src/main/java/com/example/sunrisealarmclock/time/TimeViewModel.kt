package com.example.sunrisealarmclock.time

import android.app.Application
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sunrisealarmclock.timezone.TimezoneDAO
import com.example.sunrisealarmclock.timezone.TimezoneDatabase
import com.example.sunrisealarmclock.timezone.TimezoneLocation
import com.example.sunrisealarmclock.timezone.TimezoneViewModel
import kotlinx.coroutines.launch

class TimeViewModel(application: Application) : AndroidViewModel(application){
    private val dao = SunriseTimeDatabase.getInstance(application).dao()
    private val repo = TimeRepository(dao, application)
    private lateinit var timezoneDao: TimezoneDAO
    var sunriseTime : MutableLiveData<Long> = MutableLiveData()

    init{
        viewModelScope.launch {
            repo.tryRefreshSunriseInfo()
            timezoneDao = TimezoneDatabase.getInstance(application).dao()
            val city = timezoneDao.getCurrentLocationLive()
            observeCity(city)
        }
        repo.latestSunriseTime.observeForever { latestTime ->
            sunriseTime.postValue(latestTime)
        }
    }

    private fun observeCity(city: LiveData<TimezoneLocation>){
        city.observeForever { timezone ->
            viewModelScope.launch {
                if(repo.city==null){
                    repo.city = "London"//timezone.city
                    repo.forceRefreshSunriseInfo()
                }
                else if(timezone?.city!=null && timezone.city!=repo.city){
                    repo.city = timezone.city
                    repo.forceRefreshSunriseInfo()
                }
            }
        }
    }
    fun nextSunriseTime() = sunriseTime


}