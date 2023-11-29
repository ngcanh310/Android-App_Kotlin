package com.example.mylife.ui.exercise

import android.annotation.SuppressLint
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
import com.example.mylife.data.Activity.UserActivity
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.ui.AppViewModelProvider
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter


object ActivityDestination : navigationDestination {
    override val route = "navigateToActivity"
    override val titleRes = R.string.EXERLIST
    //const val itemIdArg = "exerId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ActivityItem(
    activity: UserActivity,
    onDeleteActivity: (UserActivity) -> Unit
) {
    Box(
        modifier = Modifier
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
                    painter = painterResource(R.drawable.exercise),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = activity.activity,
                )
                Spacer(modifier = Modifier.width(3.dp))
            }
            Text(text = "${activity.calories_consume} Kcal/${activity.time}minutes")
            IconButton(onClick = { onDeleteActivity(activity) }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun MyLazyColumActivity(
    activityList: List<UserActivity>,
    onDeleteActivity: (UserActivity) -> Unit

) {
    LazyColumn {
        items(items = activityList, key = { it.user_activity_id }) { activity ->

            ActivityItem(activity, onDeleteActivity)
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
    val calendarState = rememberSheetState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val activityUiState by viewModel.activityUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            style = CalendarStyle.WEEK,
        ),
        selection = CalendarSelection.Date {
            val formattedDate = it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            viewModel.onSelectedDateChange(formattedDate)
        }
    )
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
            activityUiState.activityList,
            {
                coroutineScope.launch {
                    viewModel.deleteActivity(it)
                }
            },
            calendarState,
            selectedDate
        )
    }
}
@Composable
fun ActivityScreenBody(
    activityList: List<UserActivity>,
    onDeleteActivity: (UserActivity) -> Unit,
    calendarState: SheetState,
    selectedDate: String,
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
                .clickable(onClick = { calendarState.show() })
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
                    text = "Today, $selectedDate",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        MyLazyColumActivity(activityList, onDeleteActivity)
    }
}

