package com.example.mylife.data.AppInfo

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppInfoDao {
    @Query("SELECT * from app_info WHERE id = 1")
    fun getInfo(): AppInfo

    @Update
    suspend fun updateInfo(appInfo: AppInfo)

}