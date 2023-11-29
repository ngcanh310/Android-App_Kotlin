package com.example.mylife.data.Meal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mylife.data.Serving.Serving
import com.example.mylife.ui.meal.getCurrentDate
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert
    suspend fun insert_meal(meal: Meal)

    @Query("SELECT * FROM Meal WHERE meal_id = :id")
    fun getMeal(id: Int): Flow<Meal>

    @Query("SELECT * FROM MEAL")
    fun getAllMeal(): Flow<List<Meal>>

    @Update
    suspend fun update_meal(meal: Meal)

    @Delete
    suspend fun delte(meal: Meal)

    @Insert
    suspend fun add_serving_to_meal(meal: Meal, serving: Serving)

    @Query("SELECT * FROM serving WHERE serving.meal_id =:id")
    fun getServingFromMeal(id: Int): Flow<List<Serving>>

    @Query("SELECT * FROM meal WHERE SUBSTR(creationDate, 1, 10) = SUBSTR(:date, 1, 10)")
    fun getMealForToday(date: String): Flow<List<Meal>>

    @Query("SELECT * FROM meal ORDER BY datetime(creationDate) DESC LIMIT 1")
    fun getLatestMeal(): Flow<Meal>
}

object CurrentDateHolder {
    var currentDate by mutableStateOf(getCurrentDate().substring(0, 10))
}
