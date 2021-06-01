package com.example.sunrisealarmclock.time

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SunriseTimeEntry::class], version = 1, exportSchema = false)
abstract class SunriseTimeDatabase : RoomDatabase(){
    abstract fun dao():SunriseTimeDAO

    companion object{
        private var INSTANCE: SunriseTimeDatabase? = null

        fun getInstance(context: Context): SunriseTimeDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    SunriseTimeDatabase::class.java,
                    "sunrise_time_db")
                    .build()
            }

            return INSTANCE as SunriseTimeDatabase
        }
    }
}

