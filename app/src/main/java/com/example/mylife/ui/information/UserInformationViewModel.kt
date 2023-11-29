package com.example.mylife.ui.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.User.User
import com.example.mylife.data.User.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserInformationViewModel(
    private val userRepository: UserRepository
): ViewModel(){
    private var _userInfomationUiState = MutableStateFlow(UserInfomationUiState())
    val userInfomationUiState = _userInfomationUiState

    init {
        viewModelScope.launch {
            userRepository.getUser(1).collect{
                _userInfomationUiState.value = userInfomationUiState.value.copy(it.toUserInfo())
            }
        }
    }


}
data class UserInfomationUiState(
    val userInfo: UserInfo = UserInfo()
)

fun User.toUserInfo(): UserInfo = UserInfo(
    userName = user_name,
    userGender = user_gender,
    userAge = user_age.toString(),
    userHeight = user_height.toString(),
    userWeight = user_weight.toString(),
    userActivityRate = user_activity_rate.toString(),
    userBmi = user_bmi,
    userTdee = user_tdee,
    userAim = user_aim.toString(),
)

