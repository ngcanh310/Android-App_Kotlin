package com.example.mylife.ui.exercise

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider
import kotlinx.coroutines.launch

object AddExerDestination : navigationDestination {
    override val route = "navigateAddExer"
    override val titleRes = R.string.ADDEXER
    const val itemIdArg = "exerId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseScreen(navigateToActivity: () -> Unit,
                      navigateToUser: () -> Unit,
                      navigateToHome: () -> Unit,
                      viewModel: AddExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)
                      ) {
    val searchText by viewModel.searchText.collectAsState()
    val activity by viewModel.activity.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                hasUser = true,
                navigateToUserInfor = navigateToUser,
                navigateToHome = navigateToHome,
                hasHome = true
            )
        },
    ) { innerPadding ->
        AddExerciseBody(
            searchText,
            activity,
            viewModel::onSearchChange,
            {
                coroutineScope.launch {
                    viewModel.getActivity(it)
                }
            },
            viewModel.addExerciseUiState,
            viewModel::updateUiState,
            {
                coroutineScope.launch {
                    viewModel.addExercise()
                    navigateToActivity()
                }
            }
        )
    }
}

@Composable
fun AddExerciseBody(
    searchText: String,
    activity: List<com.example.mylife.data.Activity.Activity>,
    searchTextChange: (String) -> Unit = {},
    onActivityClick: (com.example.mylife.data.Activity.Activity) -> Unit,
    uiState: AddExerciseUiState,
    onValueChange: (AddExerciseUiState) -> Unit,
    saveActivity: () -> Unit
) {
    var h by remember { mutableStateOf(200.dp) }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)) {

                Spacer(modifier = Modifier.height(30.dp))
                Image(painter = painterResource(R.drawable.exer), contentDescription = null)
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Add more activity:",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(horizontalAlignment = Alignment.End) {
                    Spacer(modifier = Modifier.height(15.dp))
                    EditNumberField(
                        label = R.string.exer,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        value = searchText,
                        onValueChange = searchTextChange,
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Box {
                        if(searchText!="")
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth().height(h).zIndex(2f).background(Color.White)
                        ) {
                            items(items = activity, key = { it.activity_id }) { activity ->
                                Text(
                                    text = "${activity.activity_name}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                        .clickable {
                                            onActivityClick(activity)
                                            searchTextChange(activity.activity_name)
                                            onValueChange(uiState.copy(activity = activity))
                                            h=0.dp
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        EditNumberField(
                            label = R.string.time,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.time,
                            onValueChange = { onValueChange(uiState.copy(time = it)) },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth().zIndex(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                    )
                    {
                        Text(
                            text = "Kcal: ${uiState.calories}",
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    Button(onClick = {
                        saveActivity()
                    }
                    ) {
                        Text(text = "ADD")
                    }
                }
            }

}

