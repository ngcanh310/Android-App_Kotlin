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
                Text(text = item.activity_name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Calories: ${formatDouble(item.calories_consume)}g ", fontSize = 15.sp)
            }
            IconButton(onClick = { onAddActivity(item) }) {
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

@Composable
fun AddActivityColumn(
    activity: List<Activity>,
    searchText: String,
    searchTextChange: (String) -> Unit,
    setFavorite: (Activity) -> Unit,
    onAddActivity: (Activity) -> Unit,
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
        Spacer(modifier = Modifier.height(15.dp))
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
    isSearching: Boolean
) {
    Column() {

        Spacer(modifier = Modifier.height(70.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            AddActivityColumn(
                activity,
                searchText,
                searchTextChange,
                {},
                onAddActivity,
                isSearching
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
            setFavorite = {},
            onAddActivity = {
                coroutineScope.launch {
                    viewModel.getActivity(it)
                }
                viewModel.onAddActivityClick()
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
                    navigateToActivity()
                }
            },
            uiState = viewModel.customActivity,
            onValueChange = viewModel::updateCustomActivity
        )
    }
}
