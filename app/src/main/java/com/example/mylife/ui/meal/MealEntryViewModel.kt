package com.example.mylife.ui.meal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mylife.data.Meal.Meal
import com.example.mylife.data.Meal.MealRepository
import kotlinx.coroutines.flow.firstOrNull

class MealEntryViewModel(
    private val mealRepository: MealRepository,
): ViewModel() {
    var mealEntryUiState by mutableStateOf(MealEntryUiState())

    suspend fun getLatestMeal(): Meal? {
        val latestMealFlow = mealRepository.getLatestMeal()
        latestMealFlow.collect {
            Log.d("LatestMeal", "Latest Meal ID: ${it.meal_id}")
        }
        return latestMealFlow.firstOrNull()
    }

    fun updateUiState(uiState: MealEntryUiState) {
        mealEntryUiState = MealEntryUiState(mealName = uiState.mealName)
    }

    suspend fun addMeal() {
        mealRepository.insert_meal(mealEntryUiState.toMeal())
    }

    fun updateFoodUpdateUiState() {

    }
}

data class MealEntryUiState(
    val mealName: String = "",
)

fun MealEntryUiState.toMeal(): Meal = Meal(
    meal_id = 0,
    meal_name = mealName,
    meal_calories = 0.0,
    meal_protein = 0.0,
    meal_carb = 0.0,
    meal_fat = 0.0,
    creationDate = getCurrentDate()
)

