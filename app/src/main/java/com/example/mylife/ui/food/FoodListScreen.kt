package com.example.mylife.ui.food

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.navigation.navigationDestination
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.data.Food.Food
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider

// trang chứa thông tin của tất cả các food có trong CSDL và có thể thêm bằng dấu cộng.
// trang chứa thông tin như carot 100kcal/100g
// có button + để có thể thêm loại đồ ăn mình muốn

object FoodListDestination : navigationDestination {
    override val route = "navigateToListFood"
    override val titleRes = R.string.FOODLIST
    const val itemIdArg = "foodId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun Item(
    item: Food,
         navigateToDetailFood: () -> Unit,
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
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Text(
                    text = item.food_name,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "${item.food_calories} Kcal/100g")
            }
        }
        Button(onClick = navigateToDetailFood) {
            Text(text = "Detail")
        }
    }
}

// cho phép cuộn ghi danh sách có quá nhiều
@Composable
fun MyLazyColumn(
    navigateToDetailFood: () -> Unit,
    foodList:List<Food>
) {
    Column {
        EditNumberField(
            label = R.string.search,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = "",
            onValueChange = {
               // viewModel.updateUiState(viewModel.entryInfoUiState.userInfo.copy(userName = it))
            },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn {
            items(items = foodList, key = { it.food_id }) { item ->
                Item(item = item, navigateToDetailFood)
                Spacer(modifier = Modifier.height(10.dp))
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun rowItemFood(
    navigateToDetailFood: () -> Unit,
    navigateToUser: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: FoodListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    /*val foodListUiState by viewModel.foodListUiState.collectAsState()
    Scaffold(
        topBar = {
            TopBar(hasUser = true,
                navigateToUserInfor = navigateToUser,
                navigateToHome = navigateToHome,
                hasHome = true)
        },
    ) { innerPadding ->
        rowItemFoodBody(
            navigateToDetailFood,
            foodList = foodListUiState.foodList
        )
    }*/
}

@Composable
fun rowItemFoodBody(
                navigateToDetailFood: () -> Unit,
                foodList: List<Food>
) {
    Column() {

        Spacer(modifier = Modifier.height(70.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            MyLazyColumn(
                navigateToDetailFood,
                foodList = foodList
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

@Preview
@Composable
fun PreTestrowItemFood(){
    TestrowItemFood()
}


