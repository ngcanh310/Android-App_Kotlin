package com.example.mylife.data.Meal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val meal_id: Int = 1,
    val meal_name: String,
    val meal_calories: Double,
    val meal_protein: Double,
    val meal_carb: Double,
    val meal_fat: Double,
    @TypeConverters(Converters::class)
    val creationDate: String?

)
class Converters {
    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return dateString?.let {
            SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).parse(
                it
            )
        }
    }
    @TypeConverter
    fun toString(date: Date?): String? {
        return date?.let { SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(it) }
    }
}


