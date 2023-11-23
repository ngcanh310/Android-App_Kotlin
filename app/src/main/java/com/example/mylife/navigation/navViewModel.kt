package com.example.mylife.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.MyLifeApplication
import com.example.mylife.data.AppInfo.AppInfo
import com.example.mylife.data.AppInfo.AppInfoRepository
import com.example.mylife.ui.home.HomeDestination
import com.example.mylife.ui.welcome.WelcomeDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class navViewModel(
    private val appInfoRepository: AppInfoRepository
): ViewModel() {
    var navUiState by mutableStateOf(navUiState())
        private set

    init {
        viewModelScope.launch {
            navUiState = appInfoRepository.getInfo()
                .first()
                .tonavUiState()
        }
    }
    suspend fun updateAppInfo(){
        appInfoRepository.updateInfo(AppInfo(1,1))
    }
}
data class navUiState(
    val id: Int = 1,
    val firstTime: Int = 0
)

fun AppInfo.tonavUiState(): navUiState = navUiState(
    id = id,
    firstTime = first_time
)

fun navUiState.toAppInfo(): AppInfo = AppInfo(
    id = id,
    first_time = firstTime
)