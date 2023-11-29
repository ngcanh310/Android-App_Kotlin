package com.example.mylife.ui.food

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBarWithArg
import com.example.mylife.data.Food.Food
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider
import com.example.mylife.ui.information.formatDouble
import kotlinx.coroutines.launch


// trang chứa thông tin của tất cả các food có trong CSDL và có thể thêm bằng dấu cộng.
// trang chứa thông tin như carot 100kcal/100g
// có button + để có thể thêm loại đồ ăn mình muốn

object FoodListDestination : navigationDestination {
    override val route = "navigateToFoodList"
    override val titleRes = R.string.ADDFOOD
    const val itemIdArg = "foodId"
    const val mealIdArg = "mealId"
    val routeWithArg = "${FoodListDestination.route}/{${FoodListDestination.mealIdArg}}"
}

@Composable
fun Item(
    item: Food,
    setFavorite: (Food) -> Unit,
    onAddFood: (Food) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF473C8B))
            .padding(10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = item.food_name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Calories: ${formatDouble(item.food_calories)}g ", fontSize = 15.sp)
                Text(
                    text = "Protein: ${formatDouble(item.food_protein)}g, " +
                            "Carb: ${formatDouble(item.food_carb)}g, " +
                            "Fat: ${formatDouble(item.food_fat)}g",
                    fontSize = 15.sp
                )
            }
            IconButton(onClick = { onAddFood(item) }) {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(onClick = { setFavorite(item) }) {
                Log.d("setFavorite", "${item.is_favorite}")
                if (item.is_favorite == false) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// cho phép cuộn ghi danh sách có quá nhiều
@Composable
fun MyLazyColumn(
    foods: List<Food>,
    searchText: String,
    searchTextChange: (String) -> Unit,
    filterChange: (FilterType) -> Unit,
    filter: FilterType,
    setFavorite: (Food) -> Unit,
    onAddFood: (Food) -> Unit,
    isSearching: Boolean

) {
    Column {
        EditNumberField(
            label = R.string.search,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = searchText,
            onValueChange = searchTextChange,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        DropDownMenu(filterChange, filter)
        Spacer(modifier = Modifier.height(15.dp))
        if (searchText != "") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(items = foods, key = { it.food_id }) { food ->
                    Item(food, setFavorite, onAddFood)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun rowItemFood(
    navigateToEachMeal: (Int) -> Unit,
    navigateToUser: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: FoodListViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val searchText by viewModel.searchText.collectAsState()
    val foods by viewModel.foods.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val filterState by viewModel.filterState.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Scaffold(
        topBar = {
            TopBarWithArg(
                navigateToHome = navigateToEachMeal,
                navigateToMain = navigateToMain,
                navigateToUserInfor = navigateToUser,
                hasHome = true,
                hasUser = true,
                arg = viewModel.mealId
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddCustomActivityClick() },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },

        ) { innerPadding ->
        rowItemFoodBody(
            searchText = searchText,
            foods = foods,
            searchTextChange = viewModel::onSearchChange,
            filterChange = viewModel::onFilterChange,
            filter = filterState,
            setFavorite = {
                coroutineScope.launch {
                    viewModel.setFavorite(it)
                }
            },
            onAddFood = {
                coroutineScope.launch {
                    viewModel.getFood(it)
                }
                viewModel.onAddFoodClick()
            },
            isSearching
        )
    }
    if (viewModel.isDialogShown) {
        CustomDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                viewModel.onDismissDialog()
                coroutineScope.launch {
                    viewModel.addServing()
                    navigateToEachMeal(viewModel.foodListUiState.mealId)
                }
            },
            uiState = viewModel.foodListUiState,
            onValueChange = viewModel::updateUiState,
        )
    }
    if (viewModel.isDialogCustomShown) {
        CustomFoodDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                viewModel.onDismissDialog()
                coroutineScope.launch {
                    viewModel.addCustomFood()
                    navigateToEachMeal(viewModel.foodListUiState.mealId)
                }
            },
            uiState = viewModel.customFood,
            onValueChange = viewModel::onUpdateCustomFood,
        )
    }
}

@Composable
fun rowItemFoodBody(
    searchText: String,
    foods: List<Food>,
    searchTextChange: (String) -> Unit = {},
    filterChange: (FilterType) -> Unit = {},
    filter: FilterType,
    setFavorite: (Food) -> Unit,
    onAddFood: (Food) -> Unit,
    isSearching: Boolean
) {
    Column() {

        Spacer(modifier = Modifier.height(70.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            MyLazyColumn(
                foods,
                searchText,
                searchTextChange,
                filterChange,
                filter,
                setFavorite,
                onAddFood,
                isSearching
            )
        }
    }
}

@Composable
fun TestrowItemFood(

) {
    Column() {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null
                    )
                }
                Text(
                    text = "List Food",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.food),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Test",
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "100 Kcal/100g")
                    }
                }
                Button(onClick = {}) {
                    Text(text = "Detail")
                }
            }
        }
    }
}


@Composable
fun DropDownMenu(
    filterChange: (FilterType) -> Unit,
    filter: FilterType
) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color.DarkGray)
                .background(Color.White)
                .padding(8.dp)
                .clickable { expanded = !expanded },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${formatFilterType(filter)}",
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Default") },
                onClick = { filterChange(FilterType.Default) }
            )
            DropdownMenuItem(
                text = { Text("Favorite") },
                onClick = { filterChange(FilterType.Favorite) }
            )
            DropdownMenuItem(
                text = { Text("High Calories") },
                onClick = { filterChange(FilterType.HighInCalories) }
            )
            DropdownMenuItem(
                text = { Text("Low Calories") },
                onClick = { filterChange(FilterType.LowInCalories) }
            )
        }
    }
}

