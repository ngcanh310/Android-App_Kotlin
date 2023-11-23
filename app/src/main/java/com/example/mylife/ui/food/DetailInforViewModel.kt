package com.example.mylife.ui.food

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Serving.Serving
import com.example.mylife.data.Serving.ServingRepository
import com.example.mylife.ui.home.Nutrition
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailInforViewModel(
    savedStateHandle: SavedStateHandle,
    private val servingRepository: ServingRepository
): ViewModel(){
    private val servingId: Int = checkNotNull(savedStateHandle[DetailInforDestination.servingIdArg])

    val uiState: StateFlow<DetailUiState> = servingRepository.getServing(servingId)
        .map {
            it.toDetailUiState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DetailUiState()
        )
}

data class DetailUiState(
    var foodName: String = "",
    var quantity: String = "",
    var nutrition: Nutrition = Nutrition(0.0, 0.0, 0.0, 0.0)
)

fun Serving.toDetailUiState(): DetailUiState = DetailUiState(
    foodName = food_name,
    quantity = quantity.toString(),
    nutrition = Nutrition(serving_calories, serving_protein, serving_carb, serving_fat)
)