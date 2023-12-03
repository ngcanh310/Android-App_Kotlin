package com.example.mylife.ui.meal

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MealDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    mealName: String,
    onMealNameChange: (String) -> Unit
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
                    text = "Add new meal",
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
                            label = R.string.mealName,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            value = mealName,
                            onValueChange = onMealNameChange,
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
                            if (mealName.isEmpty()) {
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
                            text = "Add",
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
fun UpdateFoodDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    uiState: UpdateFoodUiState,
    onFoodChange: (UpdateFoodUiState) -> Unit
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
                    text = "Update ${uiState.foodName}",
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
                            onValueChange = { onFoodChange(uiState.copy(quantity = it)) },
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
                        Text("Kcal: ${uiState.calories}", fontWeight = FontWeight.Bold)
                        Text("P: ${uiState.protein}", fontWeight = FontWeight.Bold)
                        Text("C: ${uiState.carb}", fontWeight = FontWeight.Bold)
                        Text("F: ${uiState.fat}", fontWeight = FontWeight.Bold)


                    }
                    if (showError) {
                        Text(
                            "Please enter validate input",
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
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
                                text = "Update",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                }
            }
        }
    }
}