package com.example.a207381_cikguizwan_lab01

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class ApiUiState(
    val isLoading: Boolean = false,
    val data: HealthApiResponse? = null,
    val errorMessage: String? = null
)

class ApiViewModel : ViewModel() {

    var uiState by mutableStateOf(ApiUiState())
        private set

    init {
        fetchHealthStats()
    }

    fun fetchHealthStats() {
        uiState = ApiUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val response =
                    HealthApiClient
                        .apiService
                        .getGlobalHealthStats()

                uiState = ApiUiState(data = response)
            } catch (exception: Exception) {
                uiState = ApiUiState(
                    errorMessage = exception.message ?: "Unable to load health data."
                )
            }
        }
    }
}
