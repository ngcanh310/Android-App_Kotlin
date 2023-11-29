package com.example.mylife.data.Meal

import com.example.mylife.data.Serving.Serving
import kotlinx.coroutines.flow.Flow

class OfflineMealRepository(private val mealDao: MealDao) : MealRepository {
    override suspend fun delete_meal(meal: Meal) = mealDao.delte(meal)

    override fun get_Meal(id: Int): Flow<Meal> = mealDao.getMeal(id)

    override fun get_all_Meal(): Flow<List<Meal>> = mealDao.getAllMeal()
    override suspend fun insert_meal(meal: Meal) = mealDao.insert_meal(meal)

    override suspend fun update_meal(meal: Meal) = mealDao.update_meal(meal)

    override suspend fun add_serving_to_meal(meal: Meal, serving: Serving) =
        mealDao.add_serving_to_meal(meal, serving)

    override fun getServingFromMeal(id: Int): Flow<List<Serving>> = mealDao.getServingFromMeal(id)

    override fun getMealForDate(date: String): Flow<List<Meal>> = mealDao.getMealForToday(date)

    override fun getLatestMeal(): Flow<Meal> = mealDao.getLatestMeal()
}