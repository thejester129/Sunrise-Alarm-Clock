package com.example.sunrisealarmclock.tools

import androidx.room.TypeConverter

class RoomConverters {
    @TypeConverter
    fun fromPair(value: Pair<Double,Double>?): Array<Double> {
        return if(value == null){
                    arrayOf()
                } else{
                    arrayOf(value.first, value.second)
                }
    }

    @TypeConverter
    fun toPair(valueArray: Array<Double>): Pair<Double,Double> {
        return Pair(valueArray[0], valueArray[1])
    }

}