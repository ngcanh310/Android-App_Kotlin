package com.example.mylife.data.Serving

import android.util.Log
import kotlinx.coroutines.flow.Flow

interface ServingRepository {
    suspend fun insertServing(serving: Serving) {
        Log.d("ServingDao", "insert_serving called")
    }

    suspend fun deleteServing(serving: Serving)

    suspend fun updateServing(serving: Serving)

    fun getServing(id: Int): Flow<Serving>

    fun getAllServing(): Flow<List<Serving>>


}