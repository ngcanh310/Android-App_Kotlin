package com.example.mylife.ui.food

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylife.data.Food.Food
import com.example.mylife.data.Food.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodListViewModel(
    private val foodRepository: FoodRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

}
data class FoodListUiState(
    val searchQuery: String = "",
    val foodList: List<Food> = listOf()
)
