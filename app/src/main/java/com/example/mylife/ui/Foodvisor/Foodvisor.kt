package com.example.mylife.ui.Foodvisor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.mylife.R
import com.example.mylife.navigation.navigationDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

object FoodvisorDestination : navigationDestination {
    override val route = "navigateToFoodvisor"
    override val titleRes = R.string.Foodvisor
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Foodvisor(
    navigateToHome: () -> Unit
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val cameraViewModel = remember { CameraViewModel() }


    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        cameraViewModel = cameraViewModel,
        { navigateToHome() }
    )
}

@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    cameraViewModel: CameraViewModel,
    navigateToHome: () -> Unit

) {
    if (hasPermission) {
        CameraScreen(
            cameraViewModel, navigateToHome
        )
    } else {
        NoPermissionScreen(onRequestPermission)
    }
}
