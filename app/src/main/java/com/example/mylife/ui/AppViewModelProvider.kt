package com.example.mylife.ui

/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mylife.MyLifeApplication
import com.example.mylife.ui.exercise.ActivityViewModel
import com.example.mylife.ui.exercise.AddExerciseViewModel
import com.example.mylife.ui.food.DetailInforViewModel
import com.example.mylife.ui.food.FoodListViewModel
import com.example.mylife.ui.home.HomeViewModel
import com.example.mylife.ui.information.EntryInfoViewModel
import com.example.mylife.ui.information.UserInformationViewModel
import com.example.mylife.ui.meal.AddFoodViewModel
import com.example.mylife.ui.meal.AddMealViewModel
import com.example.mylife.ui.meal.EachMealViewModel
import com.example.mylife.ui.meal.MealEntryViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            Log.d("AppViewModelProvider" ,"init")
            EntryInfoViewModel(
                myLife().container.userRepository,
                myLife().container.appInfoRepository
            )
        }
        initializer {
            HomeViewModel(
                myLife().container.userRepository,
                myLife().container.mealRepository,
                myLife().container.userActivityRepository
            )
        }
        initializer {
            FoodListViewModel(
                myLife().container.foodRepository,
                myLife().container.servingRepository,
                this.createSavedStateHandle()
            )
        }
        initializer {
            DetailInforViewModel(
                this.createSavedStateHandle(),
                myLife().container.servingRepository
            )
        }
        initializer {
            UserInformationViewModel(
                myLife().container.userRepository
            )
        }
        initializer {
            AddMealViewModel(
                myLife().container.mealRepository
            )
        }
        initializer {
            MealEntryViewModel(
                myLife().container.mealRepository
            )
        }
        initializer {
            EachMealViewModel(
                myLife().container.mealRepository,
                myLife().container.servingRepository,
                this.createSavedStateHandle()
            )
        }
        initializer {
            AddFoodViewModel(
                this.createSavedStateHandle(),
                myLife().container.servingRepository,
                myLife().container.foodRepository,
            )
        }
        initializer {
            AddExerciseViewModel(
                myLife().container.userActivityRepository,
                myLife().container.activityRepository,
            )
        }
        initializer {
            ActivityViewModel(
                myLife().container.userActivityRepository
            )
        }


    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.myLife(): MyLifeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyLifeApplication)
