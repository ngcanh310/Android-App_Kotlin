package com.example.mylife.data.User

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mylife.data.Meal.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User) // Thêm người dùng

    @Update
    suspend fun updateUser(user: User) // Cập nhật người dùng

    @Query("SELECT * from user WHERE user_id = :id")
    fun getUser(id: Int): Flow<User>

}