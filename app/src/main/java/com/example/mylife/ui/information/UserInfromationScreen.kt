package com.example.mylife.ui.information


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.navigation.navigationDestination
import com.example.mylife.reuse.EditNumberField
import com.example.mylife.ui.AppViewModelProvider

object USerDestination : navigationDestination {
    override val route = "navigateToUser"
    override val titleRes = R.string.USERINFOR
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun UserInformationScreen(
    navigateToHome: () -> Unit,
    viewModel: UserInformationViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    Scaffold(
        topBar = {
            TopBar(hasUser = false,
                hasHome = true,
                navigateToHome = navigateToHome,
                user = false)
        },
    ) { innerPadding ->
        UserInformationScreenBody(
            userInfo = viewModel.userInfomationUiState.value.userInfo
        )
    }
}



@Composable
fun UserInformationScreenBody(
    userInfo: UserInfo
) {
    var gender = if(userInfo.userGender == "1") "Male" else "Female"
    var activityRate = if(userInfo.userActivityRate == "1") "Lightly Activate" else if (userInfo.userActivityRate == "2") "Moderate Activate" else "Very Activate"
    var goal = if(userInfo.userAim == "1") "Keep Weight" else if(userInfo.userAim == "2") "Gain Weight" else "Lose Weight"

    var isEditingName by remember { mutableStateOf(false) }
    var isEditingAge by remember { mutableStateOf(false) }
    var isEditingHeight by remember { mutableStateOf(false) }

    var isEditingWeight by remember { mutableStateOf(false) }
    var isEditingGen by remember { mutableStateOf(false) }

    var isEditingActivity by remember { mutableStateOf(false) }

    var isEditingGo by remember { mutableStateOf(false) }
    var nameText by remember { mutableStateOf(" ") }
    var ageText by remember { mutableStateOf(" ") }
    var heightText by remember { mutableStateOf( " ") }
    var weightText by remember { mutableStateOf(" ") }
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Image(painter = painterResource(R.drawable.hand), contentDescription = null)
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Name: ${userInfo.userName}",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = {isEditingName=true}) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingName ) {
                        EditNumberField(
                            label = R.string.name,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            value = nameText,
                            onValueChange = {
                                nameText = it
                            },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Gender: $gender",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { isEditingGen = true }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingGen ) {
                        // GenderRateRadioGroupExample(userInfo = )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Age: ${userInfo.userAge}",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { isEditingAge = true }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingAge) {

                        EditNumberField(
                            label = R.string.age,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            value = ageText,
                            onValueChange = {
                                ageText = it
                            },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth()
                        )

                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Height: ${userInfo.userHeight}",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { isEditingHeight = true }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingHeight ) {
                        EditNumberField(
                            label = R.string.height,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            value = heightText,
                            onValueChange = {
                                heightText=it
                            },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Weight: ${userInfo.userWeight}",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { isEditingWeight = true }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingWeight ) {
                        EditNumberField(
                            label = R.string.weight,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            value = weightText,
                            onValueChange = {
                                weightText=it
                            },
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Your Activity Rate: $activityRate ",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { isEditingActivity = true }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingActivity) {
                        // ActivityRateRadioGroupExample(userInfo = )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "BMI = ${userInfo.userBmi}, DEE = ${userInfo.userTdee}",
                            fontSize = 20.sp
                        )

                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Goal: $goal",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { isEditingGo = true }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = null
                            )
                        }
                    }
                    if (isEditingGo) {
                        //GoalRadioGroupExample(userInfo = )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                ) {
                    Text(
                        text = "SAVE",
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
fun Test()

{
    Column {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null
                    )
                }
                Text(
                    text = "User Information",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(R.drawable.hand), contentDescription = null)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Name: %s",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Age: %d",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Height: %d",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Weight: %d",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "BMI = %.2f, DEE %.2f",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Goal: %s",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreTest(){
    Test()
}

