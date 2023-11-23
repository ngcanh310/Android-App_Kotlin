package com.example.mylife.ui.exercise

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.ui.AppViewModelProvider


object ActivityDestination : navigationDestination {
    override val route = "navigateToActivity"
    override val titleRes = R.string.EXERLIST
    //const val itemIdArg = "exerId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ActivityItem(
    activity: UserActivity
) {
    Box(
        modifier = Modifier.border(1.dp, Color.Black)
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
                    painter = painterResource(R.drawable.exercise),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = activity.activity,
                )
            }
            Text(text = "${activity.calories_consume} Kcal/${activity.time}minutes")
        }
    }
}

@Composable
fun MyLazyColumActivity(
    activityList: List<UserActivity>
) {
    LazyColumn {
        items(items = activityList, key = { it.user_activity_id }) { activity ->

            ActivityItem(activity)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(navigateToAddExer: () -> Unit,
                   navigateToUser: () -> Unit,
                   navigateToHome: () -> Unit,
                   navigateToMain: () -> Unit ,
                   viewModel: ActivityViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val activityUiState by viewModel.ActivityUiState.collectAsState()
    Scaffold(
        topBar = {
            TopBar(
                navigateToMain = navigateToMain,
                navigateToHome=navigateToHome,
                navigateToUserInfor = navigateToUser,
                hasHome = true,
                hasUser = true
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddExer,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },

        ) { innerPadding ->
        ActivityScreenBody(
            activityUiState.activityList
        )
    }
}
@Composable
fun ActivityScreenBody(
    activityList: List<UserActivity>
) {
    Column {
        Spacer(modifier = Modifier.height(45.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "List Acitvity:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp)
                Spacer(modifier = Modifier.height(20.dp))
                MyLazyColumActivity(activityList)
            }

        }
    }
}

