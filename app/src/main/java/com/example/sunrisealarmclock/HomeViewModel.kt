package com.example.sunrisealarmclock

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var minsBeforeSunrise : Int = 0

    fun setMinsBeforeSunrise(mins : Int){
        minsBeforeSunrise = mins
    }
}