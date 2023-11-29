package com.example.mylife.data.Activity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    var activity_id: Int,
    var activity_name: String,
    var calories_consume: Double,
    var is_favorite: Boolean = false,
) {
    fun matchSearch(query: String): Boolean {
        return activity_name.contains(query)
    }
}
