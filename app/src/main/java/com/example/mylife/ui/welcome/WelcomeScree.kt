package com.example.mylife.ui.welcome

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylife.R
import com.example.mylife.TopBar
import com.example.mylife.navigation.navigationDestination

object WelcomeDestination : navigationDestination {
    override val route = "navigateToWelcome"
    override val titleRes = R.string.app_name
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navigateToEntryInfor: () -> Unit){
    Scaffold(
        topBar = {
            TopBar(
                hasHome = false,
                hasUser = false
            )
        },
        ) { innerPadding ->
        WelcomeBody(navigateToEntryInfor)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeBody(
    navigateToEntryInfor: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "WELCOME",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = navigateToEntryInfor,
                ) {
                    Text(text = "NEXT")
                }

            }
        }
    }
    }

@Composable
fun TestWelcomeScreen(
) {
    Column {
        Box {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "MyLife",
                    fontSize = 30.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "WELCOME",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Copyright© 2021 UIHUT LLC. All rights reserved",
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {  },
                ) {
                    Text(text = "NEXT")
                }

            }
        }
    }
}

@Preview
@Composable
fun PreTestWelCome(){
    TestWelcomeScreen()
}
