package com.example.mylife.ui.information


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.navigation.navigationDestination
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
    navigateToEntryInfor: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: UserInformationViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    Scaffold(
        topBar = {
            TopBar(
                hasUser = false,
                hasHome = true,
                navigateToHome = navigateToHome,
                navigateToMain = navigateToMain,
                user = false
            )
        },
    ) { innerPadding ->
        UserInformationScreenBody(
            navigateToEntryInfor,
            userInfo = viewModel.userInfomationUiState.value.userInfo
        )
    }
}



@Composable
fun UserInformationScreenBody(
    navigateToEntryInfor: () -> Unit,
    userInfo: UserInfo
) {
    var gender = if (userInfo.userGender == "1") "Male" else "Female"
    var activityRate =
        if (userInfo.userActivityRate == "1") "Lightly Activate" else if (userInfo.userActivityRate == "2") "Moderate Activate" else "Very Activate"
    var goal =
        if (userInfo.userAim == "1") "Keep Weight" else if (userInfo.userAim == "2") "Gain Weight" else "Lose Weight"

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.dp_15)),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_40)))
                Image(painter = painterResource(R.drawable.hand), contentDescription = null)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Name: ${userInfo.userName}",
                            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))

                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Gender: $gender",
                                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                            )
                            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))

                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Age: ${userInfo.userAge}",
                                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                                )
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))

                            }
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Height: ${userInfo.userHeight}",
                                        fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                                    )
                                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))

                                }
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "Weight: ${userInfo.userWeight}",
                                            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                                        )
                                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))

                                    }
                                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "Your Activity Rate: $activityRate ",
                                                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                                            )
                                            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dp_10)))

                                        }
                                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                                        Column {
                                            var BMIstatus: String =
                                                if (userInfo.userBmi < 18.5) "UnderWeight" else if (userInfo.userBmi < 25) "Normal" else if (userInfo.userBmi < 30) "OverWeight" else "Obese"

                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "BMI = ${userInfo.userBmi} ($BMIstatus)",
                                                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "Goal: $goal",
                                                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                                                )
                                                Spacer(
                                                    modifier = Modifier.width(
                                                        dimensionResource(
                                                            id = R.dimen.dp_10
                                                        )
                                                    )
                                                )

                                            }
                                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp_20)))

                                        }
                                        Column(
                                            horizontalAlignment = Alignment.End,
                                            modifier = Modifier
                                                .padding(dimensionResource(id = R.dimen.dp_15))
                                                .fillMaxWidth()
                                        ) {
                                            Button(
                                                onClick = navigateToEntryInfor,
                                            ) {
                                                Text(
                                                    text = "UPDATE",
                                                    textAlign = TextAlign.End
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



