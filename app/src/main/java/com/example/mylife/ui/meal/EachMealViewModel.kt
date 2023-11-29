package com.example.mylife.ui.meal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Meal.Meal
import com.example.mylife.data.Meal.MealRepository
import com.example.mylife.data.Serving.Serving
import com.example.mylife.data.Serving.ServingRepository
import com.example.mylife.ui.home.Nutrition
import com.example.mylife.ui.information.formatDouble
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class EachMealViewModel(
    private val mealRepository: MealRepository,
    private val servingRepository: ServingRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val mealId: Int = checkNotNull(savedStateHandle[EachMealDestination.mealIdArg])
    val eachMealUiState: StateFlow<EachMealUiState> =
        mealRepository.get_Meal(mealId)
            .combine(mealRepository.getServingFromMeal(mealId)) { meal, servingList ->
                EachMealUiState(
                    mealId = meal.meal_id,
                    mealName = meal.meal_name,
                    nutrition = caculateNutrition(servingList),
                    creationDate = meal.creationDate,
                    servingList = servingList
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = EachMealUiState()
            )

    fun caculateNutrition(servingList: List<Serving>): Nutrition {
        var protein = 0.0
        var calories = 0.0
        var carb = 0.0
        var fat = 0.0
        for (serving in servingList) {
            protein += serving.serving_protein
            calories += serving.serving_calories
            carb += serving.serving_carb
            fat += serving.serving_fat
        }
        return Nutrition(
            formatDouble(calories),
            formatDouble(protein),
            formatDouble(carb),
            formatDouble(fat)
        )
    }

    suspend fun updateMeal(uiState: EachMealUiState) {
        mealRepository.update_meal(uiState.toMeal())
    }

    suspend fun deleteFood(serving: Serving) {
        servingRepository.deleteServing(serving)
    }

}

data class EachMealUiState(
    val mealId: Int = 1,
    val mealName: String = "",
    val nutrition: Nutrition = Nutrition(0.0, 0.0, 0.0, 0.0),
    val creationDate: String? = "",
    val servingList: List<Serving> = listOf()
)

fun EachMealUiState.toMeal(): Meal = Meal(
    meal_name = mealName,
    meal_calories = nutrition.calories,
    meal_fat = nutrition.fat,
    meal_carb = nutrition.carb,
    meal_id = mealId,
    meal_protein = nutrition.protein,
    creationDate = creationDate
)