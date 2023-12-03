package com.example.mylife.data.Food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun addFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Query("SELECT * from food")
    fun getAllFood(): Flow<List<Food>>

    @Query("SELECT * from food WHERE food_id = :id")
    fun getFood(id: Int): Flow<Food>


}