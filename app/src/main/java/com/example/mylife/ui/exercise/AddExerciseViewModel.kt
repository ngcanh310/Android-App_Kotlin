package com.example.mylife.ui.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Activity.Activity
import com.example.mylife.data.Activity.ActivityRepository
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.data.Activity.UserActivityRepository
import com.example.mylife.ui.meal.getCurrentDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

class AddExerciseViewModel(
    private val userActivityRepository: UserActivityRepository,
    private val activityRepository: ActivityRepository,
): ViewModel() {
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    var addExerciseUiState by mutableStateOf(AddExerciseUiState())
    var customActivity by mutableStateOf(CustomActivity())
    private var _activity = MutableStateFlow(activityRepository.getAllActivity())
    val activity = searchText
        .debounce(500L)
        .combine(_activity) { text, activity ->
            if (text.isBlank()) {
                activity
            } else {
                activity.filter {
                    it.matchSearch(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            _activity.value
        )

    suspend fun getActivity(activity: Activity) {
        activityRepository.getActivity(activity.activity_id).collect {
            addExerciseUiState.activity = it
        }
        _searchText.value = activity.activity_name
    }

    fun onSearchChange(text: String) {
        _searchText.value = text
    }

    fun updateUiState(uiState: AddExerciseUiState) {
        addExerciseUiState = addExerciseUiState.copy(
            time = uiState.time,
            activity = uiState.activity,
        )
        if (uiState.time != "" && uiState.time.toDoubleOrNull() != null) {
            addExerciseUiState = addExerciseUiState.copy(
                calories = (uiState.time.toInt() * uiState.activity.calories_consume) / 30
            )
        }
    }

    fun updateCustomActivity(uiState: CustomActivity) {
        customActivity = customActivity.copy(
            time = uiState.time,
            activity = uiState.activity,
            calories = uiState.calories
        )
    }

    suspend fun addExercise() {
        userActivityRepository.addActivity(addExerciseUiState.toUserActivity())
    }

    suspend fun addCustomActivity() {
        userActivityRepository.addActivity(customActivity.toUserActivity())
    }

    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddActivityClick() {
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
}

data class AddExerciseUiState(
    var userActivityId: Int = 0,
    var activity: Activity = Activity(0, "", 0.0),
    var time: String = "",
    var calories: Double = 0.0,
)

fun AddExerciseUiState.toUserActivity(): UserActivity = UserActivity(
    user_activity_id = userActivityId,
    activity = activity.activity_name,
    time = time.toInt(),
    calories_consume = calories,
    creationDate = getCurrentDate(),
)

data class CustomActivity(
    var userActivityId: Int = 0,
    var activity: String = "",
    var time: String = "",
    var calories: String = "",
)

fun CustomActivity.toUserActivity(): UserActivity = UserActivity(
    user_activity_id = userActivityId,
    activity = activity,
    time = time.toInt(),
    calories_consume = calories.toDouble(),
    creationDate = getCurrentDate()
)

