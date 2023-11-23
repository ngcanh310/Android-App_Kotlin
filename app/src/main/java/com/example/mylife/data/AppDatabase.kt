package com.example.mylife.data

import android.content.Context
import android.util.Log
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mylife.data.Activity.Activity
import com.example.mylife.data.Food.Food
import com.example.mylife.data.Food.FoodDao
import com.example.mylife.data.Serving.Serving
import com.example.mylife.data.Meal.Meal
import com.example.mylife.data.Meal.MealDao
import com.example.mylife.data.Serving.ServingDao
import com.example.mylife.data.User.User
import com.example.mylife.data.User.UserDao
import com.google.gson.Gson
import java.io.FileReader
import com.example.mylife.data.Activity.ActivityDao
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.data.Activity.UserActivityDao
import com.example.mylife.data.AppInfo.AppInfo
import com.example.mylife.data.AppInfo.AppInfoDao

@Database(entities = [Food::class, Serving::class, Meal::class, User::class, Activity::class, UserActivity::class, AppInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun servingDao(): ServingDao
    abstract fun mealDao(): MealDao
    abstract fun userDao(): UserDao
    abstract fun activityDao(): ActivityDao
    abstract fun userActivityDao(): UserActivityDao
    abstract fun appInfoDao(): AppInfoDao
    companion object {
        @Volatile
        private var Instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "MyLife_Datbase")
                    .createFromAsset("database/AppDatabase")
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it
                    }
            }
            Log.d("database","databaseis createdb ")
        }
        }
}
