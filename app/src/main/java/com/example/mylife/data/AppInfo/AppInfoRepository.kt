package com.example.mylife.data.AppInfo

interface AppInfoRepository {
    fun getInfo(): AppInfo

    suspend fun updateInfo(appInfo: AppInfo)
}