package com.example.mylife.data.Meal

import com.example.mylife.data.Serving.Serving
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun insert_meal(meal: Meal)

    suspend fun update_meal(meal: Meal)

    suspend fun delete_meal(meal: Meal)

    fun get_Meal(id: Int): Flow<Meal>

    fun get_all_Meal(): Flow<List<Meal>>

    suspend fun add_serving_to_meal(meal: Meal, serving: Serving)

    fun getServingFromMeal(id: Int): Flow<List<Serving>>

    fun getMealForDate(date: String): Flow<List<Meal>>

    fun getLatestMeal(): Flow<Meal>

}