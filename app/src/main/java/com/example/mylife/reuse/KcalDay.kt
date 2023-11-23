package com.example.mylife.reuse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylife.ui.home.Nutrition
import com.example.mylife.ui.home.UserDetail

@Composable
fun KcalDay(
    userDetail: UserDetail
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Protein:",
                    fontSize = 21.sp)
                Text(text = "${userDetail.consumeNutrition.protein}/${userDetail.targetNutrition.protein}")
            }
            Spacer(modifier = Modifier.width(35.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Carbs:",
                    fontSize =  21.sp)
                Text(text = "${userDetail.consumeNutrition.carb}/${userDetail.targetNutrition.carb}")
            }
            Spacer(modifier = Modifier.width(35.dp) )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Fat:",
                    fontSize = 21.sp)
                Text(text = "${userDetail.consumeNutrition.fat}/${userDetail.targetNutrition.fat}")
            }
        }
    }
}