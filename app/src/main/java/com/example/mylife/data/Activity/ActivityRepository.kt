package com.example.mylife.data.Activity

import kotlinx.coroutines.flow.Flow


interface ActivityRepository{
    fun getActivity(id: Int): Flow<Activity>
    fun searchForActivity(search: String): Flow<List<Activity>>
    fun getAllActivity(): Flow<List<Activity>>
    suspend fun updateActivity(activity: Activity)
    suspend fun insertActivity(activity: Activity)
}