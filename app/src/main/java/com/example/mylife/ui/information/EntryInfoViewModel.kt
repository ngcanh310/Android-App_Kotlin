package com.example.mylife.ui.information


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mylife.data.AppInfo.AppInfo
import com.example.mylife.data.AppInfo.AppInfoRepository
import com.example.mylife.data.User.User
import com.example.mylife.data.User.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlin.math.round

class EntryInfoViewModel(
    private val userRepository: UserRepository,
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {
    var entryInfoUiState by mutableStateOf(EntryInfoUiState())
        private set
    var appInfo = MutableStateFlow(appInfoRepository.getInfo())

    suspend fun updateAppInfo() {
        appInfo.value = appInfo.value.copy(first_time = 1)
        appInfoRepository.updateInfo(AppInfo(1, 1))
    }

    fun updateUiState(userInfo: UserInfo) {
        entryInfoUiState =
            EntryInfoUiState(userInfo = userInfo, isEntryValid = validateInput(userInfo))
    }

    suspend fun saveUser() {
        if (validateInput()) {
            caculateTDEE()
            caculateNutrition()
            caculateBMI()
            if (userRepository.getUser(1).first() == null) {
                userRepository.add_user(entryInfoUiState.userInfo.toUser())
            } else {
                userRepository.update_ser(entryInfoUiState.userInfo.toUser())
            }
            updateAppInfo()
        }
    }
    private fun validateInput(uiState: UserInfo = entryInfoUiState.userInfo): Boolean{
        return with(uiState){
            userName.isNotBlank() && userGender.isNotBlank() && userAge.isNotBlank() && userHeight.isNotBlank()
                    && userWeight.isNotBlank() && userAim.isNotBlank() && userActivityRate.isNotBlank()
        }
    }
    private fun caculateBMI(uiState: UserInfo = entryInfoUiState.userInfo) {
        var BMI: Double
        val height: Double = uiState.userHeight.toDouble()
        val weight: Double = uiState.userWeight.toDouble()
        BMI = weight * 10000 / (height * height)
        Log.d("bmi", "$BMI")
        entryInfoUiState = entryInfoUiState.copy(userInfo = uiState.copy(userBmi = BMI))

    }
    private fun caculateTDEE(uiState: UserInfo = entryInfoUiState.userInfo){
        val height: Int = uiState.userHeight.toInt()
        val weight: Int = uiState.userWeight.toInt()
        val age: Int = uiState.userAge.toInt()
        val gender = if (uiState.userGender == "1") 5 else -161
        val userActivityRate = when(uiState.userActivityRate.toInt()){
            1 -> 1.375
            2 -> 1.55
            else -> 1.7
        }
        val tdee = (10 * weight + 6.25 * height - 5 * age + gender) * userActivityRate
        Log.d("tdeecaculate", "success")
        entryInfoUiState = entryInfoUiState.copy(userInfo = uiState.copy(userTdee = tdee.toDouble()))
    }
    private fun caculateNutrition(uiState: UserInfo = entryInfoUiState.userInfo){
        var calories = when(uiState.userAim.toInt()){
            1 -> uiState.userTdee
            2 -> uiState.userTdee - 300
            else -> uiState.userTdee + 300
        }
        var carb = 0.4*calories/4
        var protein = 0.3*calories/4
        var fat = 0.3*calories/9
        entryInfoUiState = entryInfoUiState.copy(userInfo = uiState.copy(userCalories = formatDouble(calories), userCarb = formatDouble(carb), userProtein = formatDouble(protein), userFat = formatDouble(fat)))
    }
}
data class EntryInfoUiState(
    val userInfo: UserInfo = UserInfo(),
    val isEntryValid: Boolean = false
)

data class UserInfo(
    val userId: Int = 1,
    val userName: String = "",
    val userGender: String = "",
    val userAge: String = "",
    val userHeight: String = "",
    val userWeight: String = "",
    val userActivityRate: String = "",
    val userBmi: Double = 0.0,
    val userTdee: Double = 0.0,
    val userAim: String = "",
    val userCalories: Double = 0.0,
    val userProtein: Double = 0.0,
    val userCarb: Double = 0.0,
    val userFat: Double = 0.0,
)

fun UserInfo.toUser(): User = User(
    user_id = userId,
    user_name = userName,
    user_age = userAge.toIntOrNull()?: 0,
    user_gender = userGender,
    user_height = userHeight.toIntOrNull()?:0,
    user_weight = userWeight.toIntOrNull()?:0,
    user_activity_rate = userActivityRate.toIntOrNull()?:0,
    user_bmi = formatDouble(userBmi),
    user_tdee = formatDouble(userTdee),
    user_aim = userAim.toIntOrNull()?:0,
    user_calories = formatDouble(userCalories),
    user_protein = formatDouble(userProtein),
    user_carb = formatDouble(userCarb),
    user_fat = formatDouble(userFat),
)
fun formatDouble(n: Double): Double{
    return round(n*10)/10
}
data class AppInfoState(
    var firstTime: Int = 0
)

