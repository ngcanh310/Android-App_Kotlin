package com.example.mylife.data.Activity

import kotlinx.coroutines.flow.Flow

class OfflineActivityRepository(private var activityDao: ActivityDao): ActivityRepository{
    override fun getActivity(id: Int): Flow<Activity> = activityDao.getActivity(id)

    override fun searchForActivity(search: String): Flow<List<Activity>> =
        activityDao.searchForActivity(search)

    override fun getAllActivity(): List<Activity> = activityDao.getAllActivity()
}