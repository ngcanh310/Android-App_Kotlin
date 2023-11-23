package com.example.mylife.reuse


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylife.ui.home.Nutrition
import com.example.mylife.ui.home.UserDetail

// bảng hiển thị kcal in và out trong 1 ngày
// số kcal chênh lệch nằm trong kcal total

@Composable
fun infIn(nutrition: Nutrition) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "EATEN", fontSize = 21.sp)
        Text(text = "${nutrition.calories}")
    }
}

@Composable
fun infNeed(nutrition: Nutrition) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "TARGET",
            fontSize = 21.sp
        )
        Text(text = "${nutrition.calories}")
    }
}

@Composable
fun infOut(Calories: Double) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "BURNED",
            fontSize = 21.sp
        )
        Text(text = "$Calories")
    }
}

@Composable
fun YourKcal(modifier: Modifier, userDetail: UserDetail) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        infIn(userDetail.consumeNutrition)
        Spacer(modifier = Modifier.width(35.dp))
        infNeed(userDetail.targetNutrition)
        Spacer(modifier = Modifier.width(35.dp))
        infOut(userDetail.activityCalories)
    }
}

