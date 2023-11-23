package com.example.mylife.data.AppInfo

import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface AppInfoRepository {
    fun getInfo(): Flow<AppInfo>

    suspend fun updateInfo(appInfo: AppInfo)
}