package com.example.mylife.data.User

import com.example.mylife.data.Meal.Meal
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override suspend fun add_user(user: User) = userDao.addUser(user)

    override suspend fun update_ser(user: User) = userDao.updateUser(user)

    override fun getUser(id: Int): Flow<User> = userDao.getUser(id)
}