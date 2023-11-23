package com.example.mylife

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mylife.navigation.AppNavHost
import androidx.navigation.*
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController = navController)
}

@Composable
fun TopBar(navigateToHome: () -> Unit = {},
           navigateToUserInfor: () -> Unit = {},
           navigateToMain: () -> Unit = {},
           hasHome: Boolean = true,
           hasUser: Boolean = true,
           home : Boolean = true,
           user :Boolean = true,
           justify : Arrangement.Horizontal =  if ((hasHome && hasUser) || (!home && hasUser) || (hasHome && !user)) Arrangement.SpaceBetween else Arrangement.Center ,

)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF473C8B))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = justify
        ) {
            if(hasHome==true) {
                IconButton(onClick = navigateToHome) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            if(home == false){
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Button(onClick = navigateToMain,
                modifier = Modifier.background(color = Color(0xFF473C8B))) {
                Text(
                    text = "MyLife",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterVertically),

                    color = Color.White
                )
            }

            if(hasUser){
                IconButton(onClick = navigateToUserInfor) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            if(user == false){
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}



