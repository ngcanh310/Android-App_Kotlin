package com.example.mylife.data.Food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    var food_id: Int,
    var food_name: String,
    var food_calories: Double,
    var food_protein: Double,
    var food_carb: Double,
    var food_fat: Double,
    var is_favorite: Boolean = false,
) {
    fun matchSearch(query: String): Boolean {
        return food_name.contains(query)
    }

    fun favoriteSearch(): Boolean {
        return is_favorite == true
    }
}
