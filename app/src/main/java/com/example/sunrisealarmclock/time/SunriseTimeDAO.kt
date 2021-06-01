package com.example.sunrisealarmclock.time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

@Dao
interface SunriseTimeDAO {
    @Insert(onConflict = REPLACE)
    suspend fun save(timeEntry: SunriseTimeEntry)

    @Query("SELECT * FROM sunriseTimeEntries WHERE id = 1")
    suspend fun get(): SunriseTimeEntry
}
