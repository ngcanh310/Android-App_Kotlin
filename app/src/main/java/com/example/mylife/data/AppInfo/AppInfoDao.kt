package com.example.mylife.data.AppInfo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao {
    @Query("SELECT * from app_info WHERE id = 1")
    fun getInfo(): Flow<AppInfo>

    @Update
    suspend fun updateInfo(appInfo: AppInfo)

}