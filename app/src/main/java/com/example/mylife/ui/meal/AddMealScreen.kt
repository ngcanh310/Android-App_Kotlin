package com.example.mylife.ui.meal

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider
import kotlinx.coroutines.launch

object AddMealDestination : navigationDestination {
    override val route = "navigateAddMeal"
    override val titleRes = R.string.ADDFOOD
    const val itemIdArg = "foodId"
    // val routeWithArgs = "$route/{$itemIdArg}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealScreen(
    navigateToListMeal: () -> Unit,
    navigateToUser: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToEachMeal: (Int) -> Unit,
    viewModel: MealEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopBar(
                navigateToUserInfor = navigateToUser,
                navigateToHome = navigateToHome,
                hasHome = true,
                hasUser = true
            )
        },

        ) { innerPadding ->
        AddMealBody(
            viewModel.mealEntryUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.addMeal()
                    val latestMeal = viewModel.getLatestMeal()
                    latestMeal?.let { meal ->
                        Log.d("MealID", "Latest Meal ID: ${meal.meal_id}")
                        navigateToEachMeal(meal.meal_id)
                    }
                }
            }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMealBody(
    uiState: MealEntryUiState,
    onItemValueChange: (MealEntryUiState) -> Unit = {},
    onSaveClick: () -> Unit
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit = {},
//    currentScreen: AppScreen
) {
    Spacer(modifier = Modifier.height(30.dp))
    Column {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "ADD MORE MEAL:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Column(horizontalAlignment = Alignment.End) {
                Spacer(modifier = Modifier.height(15.dp))
                EditNumberField(
                    label = R.string.Name,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    value = uiState.mealName,
                    onValueChange = { onItemValueChange(uiState.copy(it)) },
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .align(Alignment.End) // Đặt nút ở góc trên bên phải
                        .padding(16.dp)
                ) {
                    Text(text = "ADD")
                }
            }
        }
    }
}

// Hàm kiểm tra xem cả hai trường có được điền đầy đủ hay không, nếu điền đủ thông tin thì mới có thể bấm ADD
private fun areFieldsFilled(name: String): Boolean {
    return name.isNotBlank()
}





