package com.example.mylife.data.Food

import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getAllFoodStream(): List<Food>

    fun getFoodStream(id: Int): Flow<Food>

    suspend fun addFood(food: Food)

    suspend fun updateFood(food: Food)


}