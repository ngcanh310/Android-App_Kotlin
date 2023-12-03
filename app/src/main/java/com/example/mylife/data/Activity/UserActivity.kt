package com.example.mylife.data.Activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mylife.data.Meal.Converters

@Entity(tableName = "user_activity")
data class UserActivity (
    @PrimaryKey(autoGenerate = true)
    var user_activity_id: Int = 0,
    var activity: String,
    var time: Int,
    var calories_consume: Double,
    @TypeConverters(Converters::class)
    var creationDate: String?
)