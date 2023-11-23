package com.example.mylife.data.Activity

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface UserActivityRepository {
    suspend fun addActivity(userActivity: UserActivity)
    suspend fun deleteActivity(userActivity: UserActivity)
    suspend fun updateActivity(userActivity: UserActivity)
    fun getActivity(id: Int): Flow<UserActivity>
    fun getActivityForToday(): Flow<List<UserActivity>>

}