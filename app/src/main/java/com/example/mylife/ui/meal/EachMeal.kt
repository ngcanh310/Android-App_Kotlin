package com.example.mylife.ui.meal

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.data.Serving.Serving
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.ui.AppViewModelProvider
import com.example.mylife.ui.home.Nutrition
import kotlinx.coroutines.launch

// trang chứa thông tin của tất cả các food có trong CSDL và có thể thêm bằng dấu cộng.
// trang chứa thông tin như carot 100kcal/100g
// có button + để có thể thêm loại đồ ăn mình muốn

object EachMealDestination : navigationDestination {
    override val route = "navigateToEachMeal"
    override val titleRes = R.string.MEALLIST
    const val mealIdArg = "mealId"
    val routeWithArg = "$route/{$mealIdArg}"
    // const val itemIdArg = "foodId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

// từng phần tử
@Composable
fun Item(
    serving: Serving,
    navigateToDetailFood: (Int) -> Unit,
    onDeleteFood: (Serving) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { navigateToDetailFood(serving.serving_id) }
            .border(color = Color(0xFF473C8B), width = 2.dp, shape = RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.food),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column() {
                    Text(
                        text = serving.food_name,
                    )
                }
            }
            Row {
                IconButton(onClick = { onDeleteFood(serving) }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

// hàm cho phép cuộn khi có quá nhiều phần tử
@Composable
fun MyLazyColumnMeal(
    navigateToDetailFood: (Int) -> Unit,
    servingList: List<Serving>,
    mealName: String,
    uiState: EachMealUiState,
    onDeleteFood: (Serving) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn() {
            items(items = servingList, key = { it.serving_id }) { serving ->
                Item(serving = serving, navigateToDetailFood, onDeleteFood)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rowItemEachMeal(
    navigateToAddFood: (Int) -> Unit,
    navigateToUser: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToMain: () -> Unit ,
    navigateToDetailFood: (Int) -> Unit,
    viewModel: EachMealViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val coroutineScope = rememberCoroutineScope()
    val eachMealUiState by viewModel.eachMealUiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                navigateToMain = {
                    coroutineScope.launch {
                        viewModel.updateMeal(eachMealUiState)
                        Log.d("meal", "$eachMealUiState")
                        navigateToMain()

                    }
                },
                navigateToHome = {
                    coroutineScope.launch {
                        viewModel.updateMeal(eachMealUiState)
                        Log.d("meal", "$eachMealUiState")
                        navigateToHome()
                    }
                },
                navigateToUserInfor = navigateToUser,
                hasHome = true,
                hasUser = true
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        navigateToAddFood(viewModel.mealId)
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },

        ) { innerPadding ->
        rowItemFoodMealBody(
            eachMealUiState,
            navigateToDetailFood,
            {
                coroutineScope.launch {
                    viewModel.deleteFood(it)
                }
            }
        )
        Log.d("eachMealUiState", "$eachMealUiState")

    }
}

@Composable
fun rowItemFoodMealBody(
    uiState: EachMealUiState,
    navigateToDetailFood: (Int) -> Unit,
    onDeleteFood: (Serving) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 25.dp,
                    ambientColor = Color.Gray,
                    spotColor = Color.Black,
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Foods in ${uiState.mealName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        NutritionInfoRow(uiState.nutrition)
        Spacer(modifier = Modifier.height(15.dp))

        MyLazyColumnMeal(
            navigateToDetailFood,
            uiState.servingList,
            uiState.mealName,
            uiState = uiState,
            onDeleteFood = onDeleteFood
        )
    }

}

@Composable
fun NutritionInfoRow(
    nutrition: Nutrition,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Kcal: ${nutrition.calories}",
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "P: ${nutrition.protein}")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "C: ${nutrition.carb}")
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "F: ${nutrition.fat}")


    }
}