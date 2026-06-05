package com.example.a207381_cikguizwan_lab01

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HealthViewModel(
    application: Application
) : AndroidViewModel(application) {

    var data by mutableStateOf(
        HealthData()
    )

    private val repository: HealthRepository

    init {

        val dao =
            HealthDatabase
                .getDatabase(application)
                .healthDao()

        repository =
            HealthRepository(dao)
    }

    fun saveSteps(
        steps: String
    ) {

        data = data.copy(
            steps = steps
        )

        viewModelScope.launch {

            repository.insert(
                HealthEntity(
                    steps = steps
                )
            )
        }
    }
}