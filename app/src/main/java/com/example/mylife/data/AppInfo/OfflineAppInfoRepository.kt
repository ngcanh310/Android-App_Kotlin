package com.example.mylife.data.AppInfo

import kotlinx.coroutines.flow.Flow

class OfflineAppInfoRepository(private val appInfoDao: AppInfoDao): AppInfoRepository {
    override fun getInfo(): Flow<AppInfo> = appInfoDao.getInfo()

    override suspend fun updateInfo(appInfo: AppInfo) = appInfoDao.updateInfo(appInfo)
}