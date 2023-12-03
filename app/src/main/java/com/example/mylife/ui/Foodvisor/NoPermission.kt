package com.example.mylife.ui.Foodvisor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NoPermissionScreen(
    onRequestPermission: () -> Unit
) {
    NoPermissionContent(
        onRequestPermission = onRequestPermission
    )
}

@Composable
fun NoPermissionContent(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Please grant permission")
        Button(onClick = onRequestPermission) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Text(text = "Grant Permission")
        }
    }
}