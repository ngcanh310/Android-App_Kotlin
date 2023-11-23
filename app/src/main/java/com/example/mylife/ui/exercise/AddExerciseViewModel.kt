package com.example.mylife.ui.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.stateIn

class AddExerciseViewModel(
    private val userActivityRepository: UserActivityRepository,
    private val activityRepository: ActivityRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    var addExerciseUiState by mutableStateOf(AddExerciseUiState())

    private var _activity = MutableStateFlow(activityRepository.getAllActivity())
    val activity = searchText
        .combine(_activity) { text, activity ->
            if (text.isBlank()) {
                listOf()
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

    fun onSearchChange(text: String) {
        _searchText.value = text
    }

    suspend fun getActivity(activity: Activity) {
        activityRepository.getActivity(activity.activity_id).collect {
            addExerciseUiState.activity = it
        }
        _searchText.value = activity.activity_name
    }

    fun updateUiState(uiState: AddExerciseUiState) {
        addExerciseUiState = addExerciseUiState.copy(
            time = uiState.time,
            activity = uiState.activity,
        )
        if (validateInput()) {
            addExerciseUiState = addExerciseUiState.copy(
                calories = (uiState.time.toInt() * uiState.activity.calories_consume)/30
            )
        }
    }

    suspend fun addExercise() {
        if (validateInput()) {
            userActivityRepository.addActivity(addExerciseUiState.toUserActivity())
        }
    }

    private fun validateInput(uiState: AddExerciseUiState = addExerciseUiState): Boolean {
        return with(uiState) {
            time.isNotBlank() && activity.activity_id != 0
        }
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