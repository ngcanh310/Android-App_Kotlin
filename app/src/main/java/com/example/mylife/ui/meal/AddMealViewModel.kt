package com.example.mylife.ui.meal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Meal.Meal
import com.example.mylife.data.Meal.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddMealViewModel(
    private val mealRepository: MealRepository
): ViewModel() {
    private var _selectedDate = MutableStateFlow(getCurrentDate().substring(0, 10))
    val selectedDate = _selectedDate.asStateFlow()
    var mealName by mutableStateOf("")
    val uiState = _selectedDate.flatMapLatest { selectedDate ->
        combine(
            MutableStateFlow(selectedDate),
            mealRepository.getMealForDate(selectedDate)
        ) { date, mealList ->
            AddMealUiState(mealList)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        AddMealUiState()
    )

    fun onSelectedDateChange(date: String) {
        _selectedDate.value = date
    }

    suspend fun deleteMeal(meal: Meal) {
        mealRepository.delete_meal(meal)
    }

    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddMealClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    suspend fun addMeal() {
        mealRepository.insert_meal(Meal(0, mealName, 0.0, 0.0, 0.0, 0.0, getCurrentDate()))
    }

    fun updateMealName(value: String) {
        mealName = value
    }
}
fun getCurrentDate(): String{
    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Calendar.getInstance().time)
}
data class AddMealUiState(
    var mealList: List<Meal> = listOf(),
)