package com.example.mylife.ui.food

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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodListViewModel(
    private val foodRepository: FoodRepository,
    private val servingRepository: ServingRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val mealId: Int = checkNotNull(savedStateHandle[FoodListDestination.mealIdArg])
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private var _filterState = MutableStateFlow<FilterType>(FilterType.Default)
    val filterState = _filterState.asStateFlow()

    var foodListUiState by mutableStateOf(FoodListUiState(mealId = mealId))

    var customFood by mutableStateOf(CustomFood())
    private var _foods = MutableStateFlow<List<Food>>(emptyList())

    init {
        viewModelScope.launch {
            foodRepository.getAllFoodStream()
                .collect { foods ->
                    _foods.value = foods
                }
        }
    }

    val foods = searchText
        .debounce(500L)
        .combine(_foods) { text, foods ->
            if (text.isBlank()) {
                foods
            } else {
                foods.filter {
                    it.matchSearch(text)
                }
            }
        }
        .combine(_filterState) { combined, filter ->
            filterFoods(combined, filter)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _foods.value
        )

    fun onSearchChange(text: String) {
        _searchText.value = text
    }

    fun onFilterChange(filter: FilterType) {
        _filterState.value = filter
    }

    fun onUpdateCustomFood(uiState: CustomFood) {
        customFood = customFood.copy(
            foodName = uiState.foodName,
            calories = uiState.calories,
            carb = uiState.carb,
            fat = uiState.fat,
            protein = uiState.protein
        )
    }

    suspend fun updateFoods() {
        _foods.value = foodRepository.getAllFoodStream().first()
    }

    suspend fun addCustomFood() {
        foodRepository.addFood(customFood.toFood())
    }

    private fun filterFoods(foods: List<Food>, filter: FilterType): List<Food> {
        return when (filter) {
            FilterType.Default -> foods
            FilterType.Favorite -> foods.filter { it.is_favorite }
            FilterType.HighInCalories -> foods.sortedByDescending { it.food_calories }
            FilterType.LowInCalories -> foods.sortedBy { it.food_calories }
        }
    }

    suspend fun setFavorite(food: Food) {
        foodRepository.updateFood(food.copy(is_favorite = !food.is_favorite))
    }

    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddFoodClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    var isDialogCustomShown by mutableStateOf(false)
        private set

    fun onAddCustomActivityClick() {
        isDialogCustomShown = true
    }

    fun onDismissCustomDialog() {
        isDialogCustomShown = false
    }


    suspend fun getFood(food: Food) {
        foodRepository.getFoodStream(food.food_id).collect {
            foodListUiState.food = it
        }
        _searchText.value = food.food_name
    }

    fun updateUiState(uiState: FoodListUiState) {
        foodListUiState = foodListUiState.copy(
            quantity = uiState.quantity, food = uiState.food,
        )
        if (uiState.quantity != "" && uiState.quantity.toDoubleOrNull() != null) {
            foodListUiState = foodListUiState.copy(
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
        if (foodListUiState.quantity != "" && foodListUiState.quantity.toDoubleOrNull() != null) {
            servingRepository.insertServing(foodListUiState.toServing())
        }
    }
}

data class FoodListUiState(
    var food: Food = Food(0, "", 0.0, 0.0, 0.0, 0.0),
    var mealId: Int = 0,
    var quantity: String = "",
    var nutrition: Nutrition = Nutrition(0.0, 0.0, 0.0, 0.0)
)

enum class FilterType {
    Default,
    Favorite,
    HighInCalories,
    LowInCalories,
}

fun formatFilterType(filterType: FilterType): String {
    return when (filterType) {
        FilterType.Default -> "Default"
        FilterType.Favorite -> "Favorite"
        FilterType.HighInCalories -> "High In Calories"
        FilterType.LowInCalories -> "Low In Calories"
    }
}

fun FoodListUiState.toServing(): Serving = Serving(
    serving_id = 0,
    meal_id = mealId,
    food_name = food.food_name,
    quantity = quantity.toDouble(),
    serving_calories = nutrition.calories,
    serving_protein = nutrition.protein,
    serving_carb = nutrition.carb,
    serving_fat = nutrition.fat

)

data class CustomFood(
    var foodName: String = "",
    var calories: String = "",
    var protein: String = "",
    var carb: String = "",
    var fat: String = ""

)

fun CustomFood.toFood(): Food = Food(
    food_id = 0,
    food_name = foodName,
    food_calories = calories.toDouble(),
    food_protein = protein.toDouble(),
    food_carb = carb.toDouble(),
    food_fat = fat.toDouble(),
    is_favorite = false
)
