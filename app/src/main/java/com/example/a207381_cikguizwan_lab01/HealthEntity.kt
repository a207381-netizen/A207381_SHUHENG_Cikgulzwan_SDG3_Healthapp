package com.example.a207381_cikguizwan_lab01

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_table")
data class HealthEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val steps: String
)