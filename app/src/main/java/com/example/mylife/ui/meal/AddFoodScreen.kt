package com.example.mylife.ui.meal

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.data.Food.Food
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider
import com.example.mylife.ui.home.Nutrition
import kotlinx.coroutines.launch

object AddFoodDestination : navigationDestination {
    override val route = "navigateAddFood"
    override val titleRes = R.string.ADDFOOD
    const val itemIdArg = "foodId"
    const val mealIdArg = "mealId"
    val routeWithArg = "${AddFoodDestination.route}/{${AddFoodDestination.mealIdArg}}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    navigateToEachMeal: (Int) -> Unit,
    navigateToUser: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToMain: () -> Unit ,
    viewModel: AddFoodViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val searchText by viewModel.searchText.collectAsState()
    val foods by viewModel.foods.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopBar(
                navigateToMain = navigateToMain,
                navigateToUserInfor =navigateToUser,
                navigateToHome = navigateToHome,
                hasHome = true,
                hasUser = true
            )
        },

        ) { innerPadding ->
        AddFoodBody(
            searchText = searchText,
            foods = foods,
            searchTextChange = viewModel::onSearchChange,
            onFoodClick = {
                coroutineScope.launch {
                    viewModel.getFood(it)
                }
            },
            uiState = viewModel.addFoodUiState,
            onValueChange = viewModel::updateUiState,
            saveFood = {
                coroutineScope.launch {
                    viewModel.addServing()
                    navigateToEachMeal(viewModel.addFoodUiState.mealId)
                }
            }
        )
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodBody(
    searchText: String,
    foods: List<Food>,
    searchTextChange: (String) -> Unit = {},
    onFoodClick: (Food) -> Unit,
    uiState: AddFoodUiState,
    onValueChange: (AddFoodUiState) -> Unit = {},
    saveFood: () -> Unit,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit = {},
//    currentScreen: AppScreen
) {
    var h by remember { mutableStateOf(200.dp) }
        Spacer(modifier = Modifier.height(30.dp))
        Column {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "ADD MORE FOOD:",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(15.dp))
                    EditNumberField(
                        label = R.string.Name,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        value = searchText,
                        onValueChange = searchTextChange,
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .fillMaxWidth()
                    )
                    Box {
                        Spacer(modifier = Modifier.height(15.dp))
                        if (searchText != "") {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(h).zIndex(2f).background(Color.White)
                            ) {
                                items(items = foods, key = { it.food_id }) { food ->
                                    Text(
                                        text = "${food.food_name}",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp)
                                            .clickable {
                                                onFoodClick(food)
                                                searchTextChange(food.food_name)
                                                onValueChange(uiState.copy(food = food))
                                                h = 0.dp
                                            }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        EditNumberField(
                            label = R.string.quantity,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.quantity,
                            onValueChange = { onValueChange(uiState.copy(quantity = it)) },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth().zIndex(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                inforFood(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    nutrition = uiState.nutrition
                )
                Spacer(modifier = Modifier.height(15.dp))
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            saveFood()
                        },
                        modifier = Modifier
                            .align(Alignment.End) // Đặt nút ở góc trên bên phải
                            .padding(16.dp)
                    ) {
                        Text(text = "ADD")
                    }
                }
            }
        }
    }

// Hàm kiểm tra xem cả hai trường có được điền đầy đủ hay không, nếu điền đủ thông tin thì mới có thể bấm ADD
private fun areFieldsFilled(name: String, quantity: String): Boolean {
    return name.isNotBlank() && quantity.isNotBlank()
}

@Composable
fun inforFood(
    modifier: Modifier,
    nutrition: Nutrition
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Kcal:",
                fontSize = 20.sp
            )
            Text(text = "${nutrition.calories}")
        }
        Spacer(modifier = Modifier.width(17.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Protein:",
                fontSize = 20.sp
            )
            Text(text = "${nutrition.protein}")
        }
        Spacer(modifier = Modifier.width(17.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Carbs:",
                fontSize = 20.sp
            )
            Text(text = "${nutrition.carb}")
        }
        Spacer(modifier = Modifier.width(17.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Fat:",
                fontSize = 20.sp
            )
            Text(text = "${nutrition.fat}")
        }

    }
}




