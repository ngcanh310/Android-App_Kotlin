package com.example.mylife.data.Meal

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mylife.data.Serving.Serving

data class MealWithServing(
    @Embedded
    val meal: Meal,
    @Relation(
        parentColumn = "mealId",
        entityColumn = "servingId",
        )
    val servings: List<Serving>
)

