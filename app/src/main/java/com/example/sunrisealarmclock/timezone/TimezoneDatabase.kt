package com.example.sunrisealarmclock.timezone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [TimezoneLocation::class], version = 1, exportSchema = true)

abstract class TimezoneDatabase : RoomDatabase(){
    abstract fun dao(): TimezoneDAO

    companion object{
        private var INSTANCE: TimezoneDatabase? = null

        suspend fun getInstance(context: Context): TimezoneDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    TimezoneDatabase::class.java,
                    "timezone_db")
                    .build()
                initData()
            }
            return INSTANCE as TimezoneDatabase
        }


        private suspend fun initData(){
            val dao = INSTANCE?.dao()
            if(dao != null && dao.getAllLocations().isNullOrEmpty()){
                dao.save(TimezoneLocation("London","uk",0,50.0,50.0,true))
                dao.save(TimezoneLocation("Berlin","de",-1, 70.0,50.0,false))
            }
        }
    }
}