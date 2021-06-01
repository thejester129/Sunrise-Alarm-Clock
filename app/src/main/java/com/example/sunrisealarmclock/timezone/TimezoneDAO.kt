package com.example.sunrisealarmclock.timezone

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sunrisealarmclock.time.SunriseTimeEntry

@Dao
interface TimezoneDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entry: TimezoneLocation)

    @Query("SELECT * FROM timezones WHERE isCurrentLocation = 1 LIMIT 1")
    suspend fun getCurrentLocation(): TimezoneLocation?

    @Query("SELECT * FROM timezones")
    suspend fun getAllLocations(): Array<TimezoneLocation>?

    @Query("UPDATE timezones SET isCurrentLocation = 1 WHERE city = :city")
    suspend fun setAsCurrentLocation(city:String)

    @Query("UPDATE timezones SET isCurrentLocation = 0")
    suspend fun resetCurrentLocation()
}
