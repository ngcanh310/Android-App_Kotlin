package com.example.mylife.data.Activity

import kotlinx.coroutines.flow.Flow

class OfflineActivityRepository(private var activityDao: ActivityDao): ActivityRepository{
    override fun getActivity(id: Int): Flow<Activity> = activityDao.getActivity(id)

    override suspend fun insertActivity(activity: Activity) = activityDao.insertActivity(activity)
    override fun searchForActivity(search: String): Flow<List<Activity>> =
        activityDao.searchForActivity(search)

    override fun getAllActivity(): Flow<List<Activity>> = activityDao.getAllActivity()

    override suspend fun updateActivity(activity: Activity) = activityDao.updateActivity(activity)
}