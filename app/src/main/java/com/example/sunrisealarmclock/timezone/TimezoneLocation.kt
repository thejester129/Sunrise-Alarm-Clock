package com.example.sunrisealarmclock.timezone

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sunrisealarmclock.tools.RoomConverters

@Entity(tableName = "timezones")
class TimezoneLocation(@PrimaryKey
                       val city:String,
                       val offset:Int,
                       val x_coordinate:Double,
                       val y_coordinate:Double,
                       val isCurrentLocation:Boolean
                       )



