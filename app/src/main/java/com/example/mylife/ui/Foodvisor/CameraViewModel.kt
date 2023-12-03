package com.example.mylife.ui.Foodvisor


import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CameraViewModel(
) : ViewModel() {

    private var _currentImg = MutableStateFlow<ImageProxy?>(null)

    private var _isCameraEnabled = MutableStateFlow(true)
    val isCameraEnabled: StateFlow<Boolean> get() = _isCameraEnabled
    fun updateCurrentImage(image: ImageProxy?) {
        _currentImg.value = image
    }

    var foodInfo by mutableStateOf(FoodAnalyze())
    private var _isFoodDetected = MutableStateFlow(false)
    val isFoodDetected = _isFoodDetected.asStateFlow()
    fun turnOnandOff() {
        _isCameraEnabled.value = !_isCameraEnabled.value
    }

    fun updateDetectedTrue() {
        _isFoodDetected.value = true
    }

    fun updateDetectedFalse() {
        _isFoodDetected.value = false
    }

    fun analyzeFood() {
        viewModelScope.launch {
            _currentImg.value?.let { convertImageProxyToPart(it) }?.let {
                val response = FoodvisorApi.retrofitService.uploadImage(
                    createMultipartBody(listOf("multiple_items"), "scopes"), it,
                )
                if (response.isSuccessful) {
                    val foodDetectionResponse = response.body()
                    Log.d("Response", "$foodDetectionResponse")
                    updateDetectedTrue()
                    if (foodDetectionResponse != null && foodDetectionResponse.items.isNotEmpty()) {
                        val firstItem = foodDetectionResponse.items.first()
                        val food = firstItem.food
                        val firstfood = food.first()
                        foodInfo = FoodAnalyze(
                            firstfood.food_info.display_name,
                            firstfood.food_info.nutrition.calories_100g.toDouble(),
                            firstfood.food_info.nutrition.proteins_100g.toDouble(),
                            firstfood.food_info.nutrition.carbs_100g.toDouble(),
                            firstfood.food_info.nutrition.fat_100g.toDouble()
                        )
                        Log.d("Response", "$foodInfo")
                    } else {
                        updateDetectedFalse()
                    }
                    turnOnandOff()
                }
            }
        }
    }

    fun createMultipartBody(list: List<String>, partName: String): MultipartBody.Part {
        val requestBody = list.joinToString("\n").toRequestBody("text/plain".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, null, requestBody)
    }

    fun convertImageProxyToPart(imageProxy: ImageProxy): MultipartBody.Part {
        val buffer = imageProxy.planes[0].buffer
        val byteArray = ByteArray(buffer.remaining())
        buffer.get(byteArray)

        val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    var isDialogShown by mutableStateOf(false)
        private set

    fun onAddFoodClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }


}

data class FoodAnalyze(
    val foodName: String = "",
    val calories: Double = 0.0,
    val protein: Double = 0.0,
    val carb: Double = 0.0,
    val fat: Double = 0.0,
)
