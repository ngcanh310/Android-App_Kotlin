package com.example.mylife.ui.exercise

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.data.Activity.UserActivityRepository
import com.example.mylife.ui.information.formatDouble
import com.example.mylife.ui.meal.getCurrentDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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

    var editActivity by mutableStateOf(EditActivity())

    suspend fun deleteActivity(activity: UserActivity) {
        userActivityRepository.deleteActivity(activity)
    }

    fun onSelectedDateChange(date: String) {
        _selectedDate.value = date
    }

    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddActivityClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    suspend fun updateActivity() {
        userActivityRepository.updateActivity(editActivity.toUserActivity())
        Log.d("updated", "success")
    }

    suspend fun getActivity(activity: UserActivity) {
        editActivity =
            userActivityRepository.getActivity(activity.user_activity_id).first().toEditActivity()
        updateCalories()
    }

    var calories by mutableStateOf(0.0)

    fun updateCalories() {
        calories = editActivity.calories / editActivity.time.toInt()
    }

    fun updateEditActivity(activity: EditActivity) {
        editActivity = editActivity.copy(time = activity.time)
        if (activity.time != "" && activity.time.toIntOrNull() != null && activity.time.isDigitsOnly()) {
            editActivity =
                editActivity.copy(calories = formatDouble(calories * activity.time.toInt()))
        }
    }

}

data class ActivityUiState(
    var activityList: List<UserActivity> = listOf()
)

data class EditActivity(
    var user_activity_id: Int = 0,
    var activity_name: String = "",
    var time: String = "",
    var calories: Double = 0.0,
    var creationDate: String? = "",
)

fun UserActivity.toEditActivity(): EditActivity = EditActivity(
    user_activity_id = user_activity_id,
    activity_name = activity,
    time = time.toString(),
    calories = calories_consume,
    creationDate = creationDate
)

fun EditActivity.toUserActivity(): UserActivity = UserActivity(
    user_activity_id = user_activity_id,
    activity = activity_name,
    time = time.toInt(),
    calories_consume = calories,
    creationDate = creationDate
)