package com.example.mylife.ui.exercise

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.AddCircle
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.data.Activity.Activity
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider
import com.example.mylife.ui.information.formatDouble
import kotlinx.coroutines.launch

object AddExerDestination : navigationDestination {
    override val route = "navigateAddExer"
    override val titleRes = R.string.ADDEXER
    const val itemIdArg = "exerId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun ActivityItem(
    item: Activity,
    setFavorite: (Activity) -> Unit,
    onAddActivity: (Activity) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF473C8B))
            .padding(dimensionResource(id = R.dimen.dp_10)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = dimensionResource(id = R.dimen.dp_10)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_10)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_10))
            ) {
                Text(
                    text = item.activity_name,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                )
                Text(
                    text = "Calories: ${formatDouble(item.calories_consume)}g ",
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp
                )
            }
            IconButton(onClick = { onAddActivity(item) }) {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20))
                )
            }
            IconButton(onClick = { setFavorite(item) }) {
                Log.d("setFavorite", "${item.is_favorite}")
                if (item.is_favorite == false) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20))
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dp_20))
                    )
                }
            }
        }
    }
}

@Composable
fun AddActivityColumn(
    activity: List<Activity>,
    searchText: String,
    searchTextChange: (String) -> Unit,
    setFavorite: (Activity) -> Unit,
    onAddActivity: (Activity) -> Unit,
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
                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_15)))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(items = activity, key = { it.activity_id }) { activity ->
                ActivityItem(
                    item = activity,
                    setFavorite = setFavorite,
                    onAddActivity = onAddActivity
                )
            }
        }

    }
}

@Composable
fun rowItemActivityBody(
    searchText: String,
    activity: List<Activity>,
    searchTextChange: (String) -> Unit = {},
    setFavorite: (Activity) -> Unit,
    onAddActivity: (Activity) -> Unit,
) {
    Column() {

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_60)))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(
                dimensionResource(id = R.dimen.dp_20),
                0.dp,
                dimensionResource(id = R.dimen.dp_20),
                0.dp
            )
        ) {
            AddActivityColumn(
                activity,
                searchText,
                searchTextChange,
                setFavorite,
                onAddActivity,
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(
    navigateToActivity: () -> Unit,
    navigateToUser: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: AddExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)
                      ) {
    val searchText by viewModel.searchText.collectAsState()
    val activity by viewModel.activity.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val isSearching by viewModel.isSearching.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                navigateToMain = navigateToMain,
                hasUser = true,
                navigateToUserInfor = navigateToUser,
                navigateToHome = navigateToHome,
                hasHome = true
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
        }
    ) { innerPadding ->
        rowItemActivityBody(
            searchText = searchText,
            activity = activity,
            searchTextChange = viewModel::onSearchChange,
            setFavorite = {
                coroutineScope.launch {
                    viewModel.setFavorite(it)
                }
            },
            onAddActivity = {
                coroutineScope.launch {
                    viewModel.getActivity(it)
                }
                viewModel.onAddActivityClick()
            },
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
                    viewModel.addExercise()
                    navigateToActivity()
                }
            },
            uiState = viewModel.addExerciseUiState,
            onValueChange = viewModel::updateUiState
        )
    }
    if (viewModel.isDialogCustomShown) {
        CustomActivityDialog(
            onDismiss = {
                viewModel.onDismissCustomDialog()
            },
            onConfirm = {
                viewModel.onDismissCustomDialog()
                coroutineScope.launch {
                    viewModel.addCustomActivity()
                    viewModel.updateActivities()
                }
            },
            uiState = viewModel.customActivity,
            onValueChange = viewModel::updateCustomActivity
        )
    }
}
