package com.example.mylife.ui.exercise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.data.Activity.UserActivityRepository
import com.example.mylife.ui.meal.getCurrentDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class ActivityViewModel(
    private val userActivityRepository: UserActivityRepository
) : ViewModel() {
    private var _selectedDate = MutableStateFlow(getCurrentDate().substring(0, 10))
    val selectedDate = _selectedDate.asStateFlow()
    val activityUiState = _selectedDate.flatMapLatest { selectedDate ->
        combine(
            MutableStateFlow(selectedDate),
            userActivityRepository.getActivityForDate(selectedDate)
        ) { date, activityList ->
            Log.d("date", "$date $activityList")
            ActivityUiState(activityList)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        ActivityUiState()
    )

    suspend fun deleteActivity(activity: UserActivity) {
        userActivityRepository.deleteActivity(activity)
    }

    fun onSelectedDateChange(date: String) {
        _selectedDate.value = date
    }

}

data class ActivityUiState(
    var activityList: List<UserActivity> = listOf()
)