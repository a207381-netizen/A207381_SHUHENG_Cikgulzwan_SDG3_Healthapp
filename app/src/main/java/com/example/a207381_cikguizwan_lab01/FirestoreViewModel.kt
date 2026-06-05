package com.example.a207381_cikguizwan_lab01

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration

data class FirestoreUiState(
    val message: String = "",
    val records: List<FirestoreRecord> = emptyList(),
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class FirestoreViewModel : ViewModel() {

    private val repository = FirestoreRepository()
    private var recordsListener: ListenerRegistration? = null

    var uiState by mutableStateOf(FirestoreUiState())
        private set

    init {
        listenToRecords()
    }

    fun updateMessage(
        message: String
    ) {
        uiState =
            uiState.copy(
                message = message,
                errorMessage = null,
                successMessage = null
            )
    }

    fun saveToCloud() {
        val message = uiState.message.trim()

        if (message.isEmpty()) {
            uiState =
                uiState.copy(
                    errorMessage = "Please enter a health message.",
                    successMessage = null
                )
            return
        }

        uiState =
            uiState.copy(
                isSaving = true,
                errorMessage = null,
                successMessage = null
            )

        repository.saveHealthMessage(
            message = message,
            onSuccess = {
                uiState =
                    uiState.copy(
                        message = "",
                        isSaving = false,
                        successMessage = "Saved to cloud.",
                        errorMessage = null
                    )
            },
            onError = { error ->
                uiState =
                    uiState.copy(
                        isSaving = false,
                        errorMessage = error,
                        successMessage = null
                    )
            }
        )
    }

    private fun listenToRecords() {
        recordsListener =
            repository.listenToHealthRecords(
                onRecordsChanged = { records ->
                    uiState =
                        uiState.copy(
                            records = records,
                            isLoading = false,
                            errorMessage = null
                        )
                },
                onError = { error ->
                    uiState =
                        uiState.copy(
                            isLoading = false,
                            errorMessage = error
                        )
                }
            )
    }

    override fun onCleared() {
        recordsListener?.remove()
        super.onCleared()
    }
}
