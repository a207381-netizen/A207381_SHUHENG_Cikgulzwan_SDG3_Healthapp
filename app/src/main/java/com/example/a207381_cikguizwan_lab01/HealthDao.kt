package com.example.a207381_cikguizwan_lab01

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: HealthEntity)

    @Query("SELECT * FROM health_table")
    fun getAllData(): Flow<List<HealthEntity>>
}