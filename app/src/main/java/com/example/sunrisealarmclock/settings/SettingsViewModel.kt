package com.example.sunrisealarmclock.settings

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.example.sunrisealarmclock.R
import com.example.sunrisealarmclock.days.DayItem
import java.util.*


class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val app : Application = application

    fun setAlarmMinsBeforeSunrise(mins : String){
        with (sharedPref.edit()) {
            putString(app.getString(R.string.settings_alarm_mins_before_sunrise), mins)
            apply()
        }
    }

    fun getAlarmMinsBeforeSunrise():String{
        return sharedPref.getString(app.getString(R.string.settings_alarm_mins_before_sunrise), "0") ?: "0"
    }

    fun getDays():Array<DayItem> {
        return arrayOf( DayItem(Calendar.MONDAY,'M',getDayActive(Calendar.MONDAY)),
                        DayItem(Calendar.TUESDAY,'T',getDayActive(Calendar.TUESDAY)),
                        DayItem(Calendar.WEDNESDAY,'W',getDayActive(Calendar.WEDNESDAY)),
                        DayItem(Calendar.THURSDAY,'T',getDayActive(Calendar.THURSDAY)),
                        DayItem(Calendar.FRIDAY,'F',getDayActive(Calendar.FRIDAY)),
                        DayItem(Calendar.SATURDAY,'S',getDayActive(Calendar.SATURDAY)),
                        DayItem(Calendar.SUNDAY,'S',getDayActive(Calendar.SUNDAY)))
    }

    fun toggleDayOfWeekActive(dayOfWeek: Int){
        with (sharedPref.edit()) {
            putBoolean(dayOfWeek.toString(), !getDayActive(dayOfWeek))
            apply()
        }
    }

    private fun getDayActive(dayOfWeek:Int):Boolean{
        return sharedPref.getBoolean(dayOfWeek.toString(), false)
    }

    var timezoneChosen:Boolean
        get() = sharedPref.getBoolean("settings_timezone_chosen", false)
        set(value) {
            with (sharedPref.edit()) {
                putBoolean("settings_timezone_chosen", value)
                apply()
            }
        }

}