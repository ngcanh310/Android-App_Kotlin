package com.example.mylife

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import com.example.mylife.navigation.AppNavHost

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
                IconButton(onClick = { navigateToHome() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            if (home == false) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Button(
                onClick = { navigateToMain() },
                modifier = Modifier.background(color = Color(0xFF473C8B))
            ) {
                Text(
                    text = "MyLife",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterVertically),

                    color = Color.White
                )
            }

            if (hasUser) {
                IconButton(onClick = navigateToUserInfor) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            if (user == false) {
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

@Composable
fun TopBarWithArg(
    navigateToHome: (Int) -> Unit = {},
    navigateToUserInfor: () -> Unit = {},
    navigateToMain: () -> Unit = {},
    hasHome: Boolean = true,
    hasUser: Boolean = true,
    home: Boolean = true,
    user: Boolean = true,
    justify: Arrangement.Horizontal = if ((hasHome && hasUser) || (!home && hasUser) || (hasHome && !user)) Arrangement.SpaceBetween else Arrangement.Center,
    arg: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF473C8B))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = justify
        ) {
            if (hasHome == true) {
                IconButton(onClick = { navigateToHome(arg) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            /*if(home == false){
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }*/
            Button(
                onClick = navigateToMain,
                modifier = Modifier.background(color = Color(0xFF473C8B))
            ) {
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


