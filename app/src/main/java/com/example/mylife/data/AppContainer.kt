package com.example.mylife.data

import android.content.Context
import android.util.Log
import com.example.mylife.data.Activity.ActivityRepository
import com.example.mylife.data.Activity.OfflineActivityRepository
import com.example.mylife.data.Activity.OfflineUserActivityRepository
import com.example.mylife.data.Activity.UserActivityRepository
import com.example.mylife.data.AppInfo.AppInfoDao
import com.example.mylife.data.AppInfo.AppInfoRepository
import com.example.mylife.data.AppInfo.OfflineAppInfoRepository
import com.example.mylife.data.Food.FoodRepository
import com.example.mylife.data.Food.OfflineFoodRepository
import com.example.mylife.data.Meal.MealRepository
import com.example.mylife.data.Meal.OfflineMealRepository
import com.example.mylife.data.Serving.OfflineServingRepository
import com.example.mylife.data.Serving.Serving
import com.example.mylife.data.Serving.ServingRepository
import com.example.mylife.data.User.OfflineUserRepository
import com.example.mylife.data.User.UserRepository


interface AppContainer {
    val foodRepository: FoodRepository
    val servingRepository: ServingRepository
    val mealRepository: MealRepository
    val userRepository: UserRepository
    val activityRepository: ActivityRepository
    val userActivityRepository: UserActivityRepository
    val appInfoRepository: AppInfoRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val mealRepository: MealRepository by lazy {
        OfflineMealRepository(AppDatabase.getDatabase(context).mealDao())
    }
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(AppDatabase.getDatabase(context).userDao())
    }
    override val foodRepository: FoodRepository by lazy{
        OfflineFoodRepository(AppDatabase.getDatabase(context).foodDao())
    }
    override val servingRepository: ServingRepository by lazy{
        OfflineServingRepository(AppDatabase.getDatabase(context).servingDao())
    }
    override val activityRepository: ActivityRepository by lazy{
        OfflineActivityRepository(AppDatabase.getDatabase(context).activityDao())
    }
    override val userActivityRepository: UserActivityRepository by lazy{
        OfflineUserActivityRepository(AppDatabase.getDatabase(context).userActivityDao())
    }
    override val appInfoRepository: AppInfoRepository by lazy{
        OfflineAppInfoRepository(AppDatabase.getDatabase(context).appInfoDao())
    }
}