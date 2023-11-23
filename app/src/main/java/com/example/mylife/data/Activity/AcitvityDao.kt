package com.example.mylife.data.Activity

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity WHERE activity_id = :id")
    fun getActivity(id: Int): Flow<Activity>

    @Query("SELECT * FROM activity WHERE activity_name LIKE :search")
    fun searchForActivity(search: String): Flow<List<Activity>>

    @Query("SELECT * FROM activity")
    fun getAllActivity(): List<Activity>
}