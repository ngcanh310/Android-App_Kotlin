package com.example.mylife.data.Serving

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ServingDao {
    @Insert
    suspend fun insert_serving(serving: Serving)

    @Delete
    suspend fun delete_serving(serving: Serving)

    @Update
    suspend fun update_serving(serving: Serving)

    @Query("SELECT * FROM serving WHERE serving_id = :id")
    fun get_serving(id: Int): Flow<Serving>

    @Query("SELECT * FROM serving")
    fun get_all_serving(): Flow<List<Serving>>

}