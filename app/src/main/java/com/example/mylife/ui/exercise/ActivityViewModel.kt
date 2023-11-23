package com.example.mylife.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.data.Activity.UserActivityRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ActivityViewModel(
    private val userActivityRepository: UserActivityRepository
) : ViewModel() {
    val ActivityUiState = userActivityRepository.getActivityForToday()
        .map { ActivityUiState(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            ActivityUiState()
        )
}

data class ActivityUiState(
    var activityList: List<UserActivity> = listOf()
)