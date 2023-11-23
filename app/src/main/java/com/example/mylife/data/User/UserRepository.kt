package com.example.mylife.data.User

import com.example.mylife.data.Meal.Meal
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun add_user(user: User)

    suspend fun update_ser(user: User)

    fun getUser(id: Int): Flow<User>
}