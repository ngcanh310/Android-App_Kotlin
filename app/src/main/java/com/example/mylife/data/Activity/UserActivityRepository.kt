package com.example.mylife.data.Activity

import kotlinx.coroutines.flow.Flow

interface UserActivityRepository {
    suspend fun addActivity(userActivity: UserActivity)
    suspend fun deleteActivity(userActivity: UserActivity)
    suspend fun updateActivity(userActivity: UserActivity)
    fun getActivity(id: Int): Flow<UserActivity>
    fun getActivityForDate(date: String): Flow<List<UserActivity>>

}