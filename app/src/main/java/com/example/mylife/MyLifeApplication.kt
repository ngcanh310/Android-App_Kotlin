package com.example.mylife

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.mylife.data.AppDataContainer
import com.example.mylife.data.AppContainer

class MyLifeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        Log.d("MyLifeApplication", "onCreate is called")
    }

}
