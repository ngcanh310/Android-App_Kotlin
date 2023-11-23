package com.example.mylife.data.AppInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_info")
data class AppInfo(
    @PrimaryKey
    val id: Int = 1,
    val first_time: Int
)
