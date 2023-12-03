package com.example.mylife.ui.meal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EachMealViewModel(
    private val mealRepository: MealRepository,
    private val servingRepository: ServingRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val mealId: Int = checkNotNull(savedStateHandle[EachMealDestination.mealIdArg])
    val eachMealUiState: StateFlow<EachMealUiState> =
        mealRepository.get_Meal(mealId)
            .flatMapLatest { meal ->
                meal?.let {
                    mealRepository.getServingFromMeal(mealId)
                        .map { servingList ->
                            EachMealUiState(
                                mealId = meal.meal_id,
                                mealName = meal.meal_name,
                                nutrition = caculateNutrition(servingList),
                                creationDate = meal.creationDate,
                                servingList = servingList
                            )
                        }
                } ?: flowOf(EachMealUiState())
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = EachMealUiState()
            )

    var updateFoodUiState by mutableStateOf(UpdateFoodUiState())

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

    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddFoodClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    var updateNutrition by mutableStateOf(Nutrition(0.0, 0.0, 0.0, 0.0))

    fun updateNutrition() {
        updateNutrition = Nutrition(
            updateFoodUiState.calories / updateFoodUiState.quantity.toDouble(),
            updateFoodUiState.protein / updateFoodUiState.quantity.toDouble(),
            updateFoodUiState.calories / updateFoodUiState.quantity.toDouble(),
            updateFoodUiState.calories / updateFoodUiState.quantity.toDouble(),

            )
    }

    suspend fun getServing(serving: Serving) {
        updateFoodUiState = servingRepository.getServing(serving.serving_id).first().toUpdateFood()
        updateNutrition()
    }

    suspend fun updateFood() {
        servingRepository.updateServing(updateFoodUiState.toServing())
    }

    fun updateFoodUiState(uiState: UpdateFoodUiState) {
        updateFoodUiState = updateFoodUiState.copy(quantity = uiState.quantity)
        if (uiState.quantity != "" && uiState.quantity.toDoubleOrNull() != null) {
            updateFoodUiState = updateFoodUiState.copy(
                calories = formatDouble(updateNutrition.calories * uiState.quantity.toDouble()),
                protein = formatDouble(updateNutrition.protein * uiState.quantity.toDouble()),
                carb = formatDouble(updateNutrition.carb * uiState.quantity.toDouble()),
                fat = formatDouble(updateNutrition.fat * uiState.quantity.toDouble()),
            )
        }
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

data class UpdateFoodUiState(
    var servingId: Int = 1,
    var foodName: String = "",
    var calories: Double = 0.0,
    var protein: Double = 0.0,
    var carb: Double = 0.0,
    var fat: Double = 0.0,
    var mealId: Int = 1,
    var quantity: String = "",
)

fun Serving.toUpdateFood(): UpdateFoodUiState = UpdateFoodUiState(
    servingId = serving_id,
    foodName = food_name,
    calories = serving_calories,
    protein = serving_protein,
    carb = serving_carb,
    fat = serving_fat,
    mealId = meal_id,
    quantity = quantity.toString()
)

fun UpdateFoodUiState.toServing(): Serving = Serving(
    serving_id = servingId,
    food_name = foodName,
    serving_calories = calories.toDouble(),
    serving_protein = protein.toDouble(),
    serving_fat = fat.toDouble(),
    serving_carb = carb.toDouble(),
    meal_id = mealId,
    quantity = quantity.toDouble()
)