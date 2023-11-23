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
import androidx.compose.material.icons.filled.Create
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
import com.example.mylife.data.Meal.Meal
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.ui.AppViewModelProvider
import kotlinx.coroutines.launch

// trang chứa thông tin của tất cả các food có trong CSDL và có thể thêm bằng dấu cộng.
// trang chứa thông tin như carot 100kcal/100g
// có button + để có thể thêm loại đồ ăn mình muốn

object MealListDestination : navigationDestination {
    override val route = "navigateToListMeal"
    override val titleRes = R.string.MEALLIST
   // const val itemIdArg = "foodId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

// từng phần tử
@Composable
fun Item(
    meal: Meal,
    navigateToEachMeal: (Int) -> Unit,
    onDelete: (Meal) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black).padding(3.dp, 10.dp, 20.dp ,10.dp)
            ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(3.dp))
            Image(
                painter = painterResource(R.drawable.meal),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Text(
                    text = meal.meal_name,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "${meal.creationDate}")
            }
        }
        Row {
            Button(onClick = { navigateToEachMeal(meal.meal_id) }) {
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
    Spacer(modifier = Modifier.height(5.dp))
}

// hàm cho phép cuộn khi có quá nhiều phần tử
@Composable
fun MyLazyColumn(
    navigateToEachMeal: (Int) -> Unit,
    mealList: List<Meal>,
    onDelete: (Meal) -> Unit
) {
    Text(
        text = "List Meal:",
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    LazyColumn() {
        items(items = mealList, key = { it.meal_id }) { meal ->
            Item(meal = meal, navigateToEachMeal, onDelete)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rowItemListMeal(navigateToEachMeal: (Int) -> Unit,
                    navigateToAddMeal: () -> Unit,
                    navigateToUser: () -> Unit,
                    navigateToHome: () -> Unit,
                    viewModel: AddMealViewModel = viewModel(factory = AppViewModelProvider.Factory)
                    )
    {
        val uiState by viewModel.uiState.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                TopBar(

                    navigateToHome=navigateToHome,
                    navigateToUserInfor = navigateToUser,
                    hasHome = true,
                    hasUser = true
                )
            },

            floatingActionButton = {
                FloatingActionButton(
                    onClick = navigateToAddMeal,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            },

            ) { innerPadding ->
            rowItemListMealBody(
                navigateToEachMeal,
                uiState.mealList,
                {
                    coroutineScope.launch {
                        viewModel.deleteMeal(it)

                    }
                }
            )
        }
    }

@Composable
fun rowItemListMealBody(
    navigateToEachMeal: (Int) -> Unit,
    mealList: List<Meal>,
    onDelete: (Meal) -> Unit

//                canNavigateBack: Boolean,
//                navigateUp: () -> Unit = {},
//                currentScreen: AppScreen
) {
        Column(horizontalAlignment = Alignment.End,

                    modifier = Modifier.padding(20.dp, 70.dp, 20.dp, 0.dp)) {
            Column() {
                MyLazyColumn(navigateToEachMeal, mealList, onDelete)
            }

        }
        }



@Composable
fun TestListMeal(
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null
                    )
                }
                Text(
                    text = "List Meal",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(horizontalAlignment = Alignment.End,

           modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            Column() {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.food),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Carot",
                            )
                            Text(text = "100 Kcal/100g")
                        }
                    }
                    Button(onClick = {  }) {
                        Text(text ="Detail")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
            Button(
                onClick = {  },

                ) {
                Image(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)

                )
            }
        }
    }
}

@Preview
@Composable
fun PreTestListMeal(){
    TestListMeal()
}

