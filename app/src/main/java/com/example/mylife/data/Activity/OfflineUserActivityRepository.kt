package com.example.mylife.data.Activity

import kotlinx.coroutines.flow.Flow

class OfflineUserActivityRepository(private var userActivityDao: UserActivityDao): UserActivityRepository {
    override suspend fun addActivity(userActivity: UserActivity) = userActivityDao.addActivity(userActivity)
    override suspend fun deleteActivity(userActivity: UserActivity) = userActivityDao.deleteActivity(userActivity)
    override fun getActivity(id: Int): Flow<UserActivity> = userActivityDao.getActivity(id)
    override suspend fun updateActivity(userActivity: UserActivity) =
        userActivityDao.updateActivity(userActivity)

    override fun getActivityForDate(date: String): Flow<List<UserActivity>> =
        userActivityDao.getActivityForDate(date)
}