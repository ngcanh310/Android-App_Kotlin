package com.example.mylife.ui.exercise

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
import androidx.compose.material.icons.outlined.Create
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
import androidx.compose.ui.res.dimensionResource
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
    onDeleteActivity: (UserActivity) -> Unit,
    onUpdateActivity: (UserActivity) -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                color = Color(0xFF473C8B), width = 2.dp, shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.padding_small)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.exercise),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_40))
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_10))
            ) {
                Text(
                    text = activity.activity,
                )
                Text(text = "${activity.calories_consume} Kcal/${activity.time}minutes")
                Text(text = "Added at ${activity.creationDate?.substring(11, 15)}")

            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_3)))
            IconButton(onClick = {
                onUpdateActivity(activity)
                Log.d("updateClick", "ok")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Create,
                    contentDescription = null
                )
            }
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
    onDeleteActivity: (UserActivity) -> Unit,
    onUpdateActivity: (UserActivity) -> Unit
) {
    LazyColumn {
        items(items = activityList, key = { it.user_activity_id }) { activity ->

            ActivityItem(activity, onDeleteActivity, onUpdateActivity)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_10)))
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
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date {
            val formattedDate = it.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
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
            selectedDate,
            {
                viewModel.onAddActivityClick()
                coroutineScope.launch {
                    viewModel.getActivity(it)
                }
            }
        )
    }
    if (viewModel.isDialogShown) {
        EditCustomDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                viewModel.onDismissDialog()
                coroutineScope.launch {
                    viewModel.updateActivity()
                }
            },
            uiState = viewModel.editActivity,
            onValueChange = viewModel::updateEditActivity
        )
    }
}
@Composable
fun ActivityScreenBody(
    activityList: List<UserActivity>,
    onDeleteActivity: (UserActivity) -> Unit,
    calendarState: SheetState,
    selectedDate: String,
    onUpdateActivity: (UserActivity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dp_20)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_40)))
        Card(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dp_20))
                .fillMaxWidth()
                .clickable(onClick = { calendarState.show() })
                .shadow(
                    elevation = dimensionResource(id = R.dimen.dp_24),
                    ambientColor = Color.Gray,
                    spotColor = Color.Black,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_10))
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.dp_10))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_15)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$selectedDate",
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.font_large).value.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
        MyLazyColumActivity(activityList, onDeleteActivity, onUpdateActivity)
    }
}

