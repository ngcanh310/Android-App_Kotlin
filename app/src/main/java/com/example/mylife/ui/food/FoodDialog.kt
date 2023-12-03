package com.example.mylife.ui.food

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.example.mylife.R
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.Foodvisor.FoodAnalyze

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    uiState: FoodListUiState,
    onValueChange: (FoodListUiState) -> Unit = {},
) {
    var showError by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_15)),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_15)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_24))
            ) {
                Text(
                    text = "${uiState.food.food_name}",
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Please enter quantity",
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_15))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditNumberField(
                            label = R.string.quantity,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.quantity,
                            onValueChange = { onValueChange(uiState.copy(quantity = it)) },
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                                .fillMaxWidth()
                                .zIndex(1f)
                        )

                    }
                    Divider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Kcal: ${uiState.nutrition.calories}", fontWeight = FontWeight.Bold)
                        Text("P: ${uiState.nutrition.protein}", fontWeight = FontWeight.Bold)
                        Text("C: ${uiState.nutrition.carb}", fontWeight = FontWeight.Bold)
                        Text("F: ${uiState.nutrition.fat}", fontWeight = FontWeight.Bold)

                    }
                    if (showError) {
                        Text(
                            "Please enter validate input",
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_30)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            if (uiState.quantity.isEmpty() || uiState.quantity.toDoubleOrNull() == null) {
                                showError = true
                            } else {
                                onConfirm()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Confirm",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomFoodDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    uiState: CustomFood,
    onValueChange: (CustomFood) -> Unit = {},
) {
    var showError by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_15)),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_15)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_24))
            ) {
                Text(
                    text = "Add your own food",
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_15))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditNumberField(
                            label = R.string.food,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.foodName,
                            onValueChange = { onValueChange(uiState.copy(foodName = it)) },
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                                .fillMaxWidth()
                                .zIndex(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditNumberField(
                            label = R.string.kcal,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.calories,
                            onValueChange = { onValueChange(uiState.copy(calories = it)) },
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                                .fillMaxWidth()
                                .zIndex(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditNumberField(
                            label = R.string.pro,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.protein,
                            onValueChange = { onValueChange(uiState.copy(protein = it)) },
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                                .fillMaxWidth()
                                .zIndex(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditNumberField(
                            label = R.string.carb,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.carb,
                            onValueChange = { onValueChange(uiState.copy(carb = it)) },
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                                .fillMaxWidth()
                                .zIndex(1f)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditNumberField(
                            label = R.string.fat,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = uiState.fat,
                            onValueChange = { onValueChange(uiState.copy(fat = it)) },
                            modifier = Modifier
                                .padding(bottom = dimensionResource(id = R.dimen.dp_30))
                                .fillMaxWidth()
                                .zIndex(1f)
                        )
                    }

                    Divider()
                    if (showError) {
                        Text(
                            "Please enter validate input",
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_30)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            if (
                                uiState.protein.isEmpty() || uiState.protein.toDoubleOrNull() == null
                                || uiState.carb.isEmpty() || uiState.carb.toDoubleOrNull() == null
                                || uiState.fat.isEmpty() || uiState.fat.toDoubleOrNull() == null
                                || uiState.fat.isEmpty() || uiState.fat.toDoubleOrNull() == null
                                || uiState.calories.isEmpty() || uiState.calories.toDoubleOrNull() == null
                                || uiState.foodName.isEmpty()
                            ) {
                                showError = true
                            } else {
                                onConfirm()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Confirm",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FoodDetectDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    uiState: FoodAnalyze,
) {
    var showError by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_15)),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_15)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_24))
            ) {
                Text(
                    text = "${uiState.foodName}",
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_15))
                ) {
                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Kcal: ${uiState.calories}", fontWeight = FontWeight.Bold)
                        Text("P: ${uiState.protein}", fontWeight = FontWeight.Bold)
                        Text("C: ${uiState.carb}", fontWeight = FontWeight.Bold)
                        Text("F: ${uiState.fat}", fontWeight = FontWeight.Bold)

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_30)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            onConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Retry",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun NoFoodDetectDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    var showError by remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp_15)),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_15)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_24))
            ) {
                Text(
                    text = "Please try again",
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_15))
                ) {
                    Divider()

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp_30)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            onConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Retry",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

            }
        }
    }
}