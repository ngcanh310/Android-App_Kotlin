package com.example.mylife.ui.meal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Food.Food
import com.example.mylife.data.Food.FoodRepository
import com.example.mylife.data.Serving.Serving
import com.example.mylife.data.Serving.ServingRepository
import com.example.mylife.ui.home.Nutrition
import com.example.mylife.ui.information.formatDouble
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AddFoodViewModel(
    savedStateHandle: SavedStateHandle,
    private val servingRepository: ServingRepository,
    private val foodRepository: FoodRepository
): ViewModel() {
    private val mealId: Int = checkNotNull(savedStateHandle[AddFoodDestination.mealIdArg])
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    var addFoodUiState by mutableStateOf(AddFoodUiState(mealId = mealId))

    private var _foods = MutableStateFlow(foodRepository.getAllFoodStream())
    val foods = searchText
        .combine(_foods){
            text, foods ->
            if(text.isBlank()){
                listOf()
            }else{
                foods.filter {
                    it.matchSearch(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _foods.value
        )

    fun onSearchChange(text: String) {
        _searchText.value = text
    }

    suspend fun getFood(food: Food) {
        foodRepository.getFoodStream(food.food_id).collect {
            addFoodUiState.food = it
        }
        _searchText.value = food.food_name
    }

    fun updateUiState(uiState: AddFoodUiState) {
        addFoodUiState = addFoodUiState.copy(
            quantity = uiState.quantity, food = uiState.food,
        )
        if (validateInput()) {
            addFoodUiState = addFoodUiState.copy(
                nutrition = Nutrition(
                    formatDouble(uiState.food.food_calories * uiState.quantity.toDouble() / 100),
                    formatDouble(uiState.food.food_protein * uiState.quantity.toDouble() / 100),
                    formatDouble(uiState.food.food_carb * uiState.quantity.toDouble() / 100),
                    formatDouble(uiState.food.food_fat * uiState.quantity.toDouble() / 100),
                )
            )
        }
    }

    suspend fun addServing() {
        if (validateInput()) {
            servingRepository.insertServing(addFoodUiState.toServing())
        }
    }

    private fun validateInput(uiState: AddFoodUiState = addFoodUiState): Boolean {
        return with(uiState) {
            quantity.isNotBlank() && food.food_id != 0
        }
    }
}


data class AddFoodUiState(
    var food: Food = Food(0, "", 0.0, 0.0, 0.0, 0.0),
    var mealId: Int = 0,
    var quantity: String = "",
    var nutrition: Nutrition = Nutrition(0.0, 0.0, 0.0, 0.0)
) {

}

fun AddFoodUiState.toServing(): Serving = Serving(
    serving_id = 0,
    meal_id = mealId,
    food_name = food.food_name,
    quantity = quantity.toDouble(),
    serving_calories = nutrition.calories,
    serving_protein = nutrition.protein,
    serving_carb = nutrition.carb,
    serving_fat = nutrition.fat

)