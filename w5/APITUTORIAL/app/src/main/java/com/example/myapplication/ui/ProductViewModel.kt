package com.example.myapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Product
import com.example.myapplication.data.RetrofitClient
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data object Loading : ProductUiState
    data class Success(val product: Product) : ProductUiState
    data class Error(val message: String) : ProductUiState
}

class ProductViewModel : ViewModel() {

    var uiState by mutableStateOf<ProductUiState>(ProductUiState.Loading)
        private set

    init {
        loadProduct()
    }

    fun loadProduct() {
        uiState = ProductUiState.Loading
        viewModelScope.launch {
            uiState = try {
                ProductUiState.Success(RetrofitClient.api.getProduct())
            } catch (e: Exception) {
                ProductUiState.Error(e.message ?: "Không thể tải dữ liệu")
            }
        }
    }
}
