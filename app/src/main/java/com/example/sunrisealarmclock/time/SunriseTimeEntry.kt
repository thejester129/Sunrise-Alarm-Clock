package com.example.sunrisealarmclock.time

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "sunriseTimeEntries")
data class SunriseTimeEntry(val time:Long, @PrimaryKey val id: Int = 0)


