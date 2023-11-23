package com.example.mylife.ui.meal

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    navigateToDetailFood: (Int) -> Unit
) {
    Box(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
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
                Button(onClick = { navigateToDetailFood(serving.serving_id) }) {
                    Text(text = "Detail")
                }
                Spacer(modifier = Modifier.width(3.dp))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
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
) {
    Column {
        Spacer(modifier = Modifier.height(45.dp))
        Text(
            text = "Foods in $mealName:",
            modifier = Modifier.padding(23.dp, 25.dp, 23.dp, 10.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        NutritionInfoRow(nutrition = uiState.nutrition)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn() {
            items(items = servingList, key = { it.serving_id }) { serving ->
                Item(serving = serving, navigateToDetailFood)
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
                navigateToMain = navigateToMain,
                navigateToHome = navigateToHome,
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
                        viewModel.updateMeal()
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
        )
    }
}

@Composable
fun rowItemFoodMealBody(
    uiState: EachMealUiState,
    navigateToDetailFood: (Int) -> Unit,
) {
    Column() {
        Column(
        ) {
            MyLazyColumnMeal(navigateToDetailFood, uiState.servingList, uiState.mealName, uiState = uiState)
        }
        //navigateToDetailFood: (Int) -> Unit,
        //servingList: List<Serving>,
       // mealName: String,
       // uiState: EachMealUiState,
    }
}

@Composable
fun NutritionInfoRow(nutrition: Nutrition,
                     ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

            Text(
                text = "Calories: ${nutrition.calories}",
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Protein: ${nutrition.protein}")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Carb: ${nutrition.carb}")
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Fat: ${nutrition.fat}")


    }
}