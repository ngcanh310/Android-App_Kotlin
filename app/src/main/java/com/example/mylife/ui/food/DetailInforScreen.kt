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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.ui.AppViewModelProvider

//  hiển thị tất cả thông tin về food như đạm, xơ, vitamin, ...

object DetailInforDestination : navigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.DETAILINFOR
    const val servingIdArg = "itemId"
    val routeWithArgs = "$route/{$servingIdArg}"
}

@Composable
fun Item(name: String,
         modifier: Modifier= Modifier
             .border(1.dp, Color.Black)
             .padding(20.dp)
    ) {
    Box(modifier= Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center)) {
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(10.dp)
                .width(250.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name)

        }
    }
}

@Composable
fun listItem(
    foodInfo: DetailUiState
){
    Column() {
        Spacer(modifier = Modifier.height(10.dp))
        Item(name = "Quantity: ${foodInfo.quantity}gam")
        Spacer(modifier = Modifier.height(10.dp))
        Item(name = "Kcal: ${foodInfo.nutrition.calories}gam")
        Spacer(modifier = Modifier.height(15.dp))
        Item(name = "Protein: ${foodInfo.nutrition.protein}gam")
        Spacer(modifier = Modifier.height(15.dp))
        Item(name = "Carbohydrate: ${foodInfo.nutrition.carb}gam")
        Spacer(modifier = Modifier.height(15.dp))
        Item(name = "Fat: ${foodInfo.nutrition.fat}gam")
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FullInforFood(
    navigateToUser: () -> Unit,
    navigateToHome: () -> Unit,

    modifier: Modifier = Modifier,
    viewModel: DetailInforViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopBar(hasUser = true,
                navigateToUserInfor = navigateToUser,
                navigateToHome = navigateToHome,
                hasHome = true)
        },
    ) { innerPadding ->
        FullInforFoodBody(

            uiState
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun test(
 //   navigateToEachMeal: () -> Unit,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit = {},
//    currentScreen: AppScreen
    // viewModel: DetailInforViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //var uiState = viewModel.uiState.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(45.dp))
        Image(painter = painterResource(R.drawable.detail), contentDescription = null)
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Name: ",
            modifier=Modifier.padding(20.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
       // listItem(foodInfo = uiState.value)
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            , horizontalAlignment = Alignment.End) {
            Button(onClick = {  },
                ) {
                Text(text = "ADD")
            }
        }
    }
    }

@Composable
fun FullInforFoodBody(

    uiState: DetailUiState
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit = {},
//    currentScreen: AppScreen
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(45.dp))
        Image(painter = painterResource(R.drawable.detail), contentDescription = null)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Name: ${uiState.foodName}",
            modifier = Modifier.padding(20.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        listItem(foodInfo = uiState)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalAlignment = Alignment.End
        ) {
        }
    }
}


@Preview
@Composable
fun preTestFullInforFood(){
    test()
}
