package com.example.mylife.data.AppInfo

class OfflineAppInfoRepository(private val appInfoDao: AppInfoDao): AppInfoRepository {
    override fun getInfo(): AppInfo = appInfoDao.getInfo()

    override suspend fun updateInfo(appInfo: AppInfo) = appInfoDao.updateInfo(appInfo)
}