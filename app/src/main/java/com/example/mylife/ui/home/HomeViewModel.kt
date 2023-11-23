package com.example.mylife.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.data.Activity.UserActivityRepository
import com.example.mylife.data.Meal.Meal
import com.example.mylife.data.Meal.MealRepository
import com.example.mylife.data.User.UserRepository
import com.example.mylife.ui.information.formatDouble
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val userRepository: UserRepository,
    private val mealRepository: MealRepository,
    private val userActivityRepository: UserActivityRepository
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        userRepository.getUser(1)
            .combine(mealRepository.getMealForToday())
            { user, mealList ->
                HomeUiState(
                    mealList = mealList,
                    userDetail = UserDetail(
                        targetNutrition = Nutrition(
                            user.user_calories,
                            user.user_protein,
                            user.user_carb,
                            user.user_fat
                        ), consumeNutrition = caculateConsume(mealList)
                    )
                )
            }.combine(userActivityRepository.getActivityForToday()) { combined, activityList ->
                combined.copy(
                    userDetail = combined.userDetail.copy(
                        activityCalories = caculateActivityCalories(activityList)
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeUiState()
            )

    private fun caculateConsume(mealList: List<Meal>): Nutrition {
        var calories: Double = 0.0
        var protein: Double = 0.0
        var carb: Double = 0.0
        var fat: Double = 0.0
        for (meal in mealList) {
            calories += meal.meal_calories
            protein += meal.meal_protein
            carb += meal.meal_carb
            fat += meal.meal_fat
        }

        return Nutrition(formatDouble(calories), formatDouble(protein), formatDouble(carb), formatDouble(fat))
    }

    private fun caculateActivityCalories(activityList: List<UserActivity>): Double {
        var calories: Double = 0.0
        for (activity in activityList) {
            calories += activity.calories_consume
        }
        return calories
    }
}

data class HomeUiState(
    var mealList: List<Meal> = listOf(),
    var userDetail: UserDetail = UserDetail()
)

data class UserDetail(
    var targetNutrition:Nutrition = Nutrition(0.0,0.0,0.0,0.0),
    var consumeNutrition:Nutrition = Nutrition(0.0,0.0,0.0,0.0),
    var activityCalories: Double = 0.0
)

data class Nutrition(
    var calories: Double,
    var protein: Double,
    var carb: Double,
    var fat:Double
)