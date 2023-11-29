package com.example.mylife.ui.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.data.Meal.CurrentDateHolder
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.PieChart
import com.example.mylife.reuse.SecondarypieChart
import com.example.mylife.reuse.infOut
import com.example.mylife.ui.AppViewModelProvider
import com.example.mylife.ui.meal.getCurrentDate
import kotlinx.coroutines.delay

object HomeDestination : navigationDestination {
    override val route = "navigateToHome"
    override val titleRes = R.string.HOME
}

@Composable
fun Food(navigateToListFood: () -> Unit,){
    Card(
        modifier = Modifier
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .fillMaxWidth()
            .clickable(onClick = navigateToListFood)
            .shadow(
                elevation = 25.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Black,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.food),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(R.string.food),
                        )
                }
            }
        }
    }
}

@Composable
fun Activity(
    navigateToListExer: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .fillMaxWidth()
            .clickable(onClick = navigateToListExer)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.exercise),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(R.string.exercise),

                        )
                }
            }

        }
    }
}

@Composable
fun Meal(navigateToListMeal: () -> Unit,){
    Card(
        modifier = Modifier
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .fillMaxWidth()
            .clickable(onClick = navigateToListMeal)
            .shadow(
                elevation = 25.dp,
                ambientColor = Color.Gray,
                spotColor = Color.Black,
                shape = RoundedCornerShape(10.dp)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.brunch),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(R.string.Meal),

                        )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToListExer: () -> Unit,
    navigateToUser: () -> Unit,
    navigateToListMeal: () -> Unit,
    navigateToFood: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    Log.d("HOME", "${homeUiState.userDetail.consumeNutrition}")
    LaunchedEffect(true){
        while(true){
            CurrentDateHolder.currentDate = getCurrentDate().substring(0, 10)
            delay(86400000)
        }
    }
    Scaffold(
        topBar = {
            TopBar(hasUser = true,
                navigateToUserInfor = navigateToUser,
                hasHome = false,
                home=false)
        },
    ) { innerPadding ->
        HomeScreenBody(
            navigateToListExer,
            navigateToListMeal,
            navigateToFood,
            userDetail = homeUiState.userDetail
        )
    }
}

@Composable
fun goal(userDetail: UserDetail) {
    Card(
        modifier = Modifier
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .fillMaxSize()
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var remain =
                        userDetail.targetNutrition.calories - userDetail.consumeNutrition.calories + userDetail.activityCalories
                    if (remain >= -50 && remain <= 50) {
                        Image(
                            painter = painterResource(R.drawable.success),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp)
                        )
                    } else {
                        Text(text = "Keep it up! You're almost done with your diet")
                    }
                }
            }

        }
    }
}


// trang chính của app
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenBody(
    navigateToListExer: () -> Unit,
    navigateToListMeal: () -> Unit,
    navigateToListFood: () -> Unit,
    modifier: Modifier = Modifier,
    userDetail: UserDetail
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PieChart(
                    userDetail.consumeNutrition.calories,
                    userDetail.targetNutrition.calories
                )
                infOut(userDetail.activityCalories)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SecondarypieChart(
                    userDetail.consumeNutrition.protein,
                    userDetail.targetNutrition.protein,
                    "Protein"
                )
                SecondarypieChart(
                    userDetail.consumeNutrition.carb,
                    userDetail.targetNutrition.carb,
                    "Carb"
                )
                SecondarypieChart(
                    userDetail.consumeNutrition.fat,
                    userDetail.targetNutrition.fat,
                    "Fat"
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(0xFF473C8B),
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .padding(50.dp, 40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(40.dp))
                        .aspectRatio(1f)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Activity(navigateToListExer)
                        Spacer(modifier = Modifier.height(8.dp))
                        Meal(navigateToListMeal)
                        Spacer(modifier = Modifier.height(8.dp))
                        goal(userDetail)
                    }
                }
            }
        }
    }
}
