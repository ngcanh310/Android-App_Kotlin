package com.example.mylife.ui.meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Meal.Meal
import com.example.mylife.data.Meal.MealRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddMealViewModel(
    private val mealRepository: MealRepository
): ViewModel() {
    val uiState: StateFlow<AddMealUiState> =
        mealRepository.getMealForToday().map { AddMealUiState(mealList = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = AddMealUiState()
            )

    suspend fun deleteMeal(meal: Meal) {
        mealRepository.delete_meal(meal)
    }
}
fun getCurrentDate(): String{
    val dateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Calendar.getInstance().time)
}
data class AddMealUiState(
    var mealList: List<Meal> = listOf(),
)