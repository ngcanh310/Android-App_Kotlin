package com.example.mylife.ui.Foodvisor

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.mylife.ui.food.FoodDetectDialog
import com.example.mylife.ui.food.NoFoodDetectDialog

@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    navigateToHome: () -> Unit

) {
    CameraContent(
        viewModel,
        navigateToHome
    )
}

@Composable
private fun CameraContent(
    viewModel: CameraViewModel,
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }
    val isCameraEnabled by viewModel.isCameraEnabled.collectAsState()
    val isFoodDetected by viewModel.isFoodDetected.collectAsState()
    if (isCameraEnabled) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        val mainExcutor = ContextCompat.getMainExecutor(context)
                        cameraController.takePicture(
                            mainExcutor,
                            object : ImageCapture.OnImageCapturedCallback() {
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    viewModel.updateCurrentImage(image)
                                    viewModel.analyzeFood()
                                    Log.d("img", "$image")
                                    viewModel.onAddFoodClick()
                                }
                            })
                    }) {
                }
            }
        ) { paddingValues: PaddingValues ->
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        setBackgroundColor(Color.BLACK)
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                    }
                })
        }
    } else {
        if (viewModel.isDialogShown) {
            if (isFoodDetected == true) {
                FoodDetectDialog(
                    onDismiss = {
                        viewModel.onDismissDialog()
                        navigateToHome()
                    },
                    onConfirm = {
                        viewModel.onDismissDialog()
                        viewModel.turnOnandOff()
                    },
                    uiState = viewModel.foodInfo,
                )
            } else {
                NoFoodDetectDialog(
                    onDismiss = {
                        viewModel.onDismissDialog()
                        navigateToHome()
                    },
                    onConfirm = {
                        viewModel.onDismissDialog()
                        viewModel.turnOnandOff()
                    }
                )
            }

        }
    }

}

