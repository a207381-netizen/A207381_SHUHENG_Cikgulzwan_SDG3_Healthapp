package com.example.a207381_cikguizwan_lab01

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ApiScreen(
    navController: NavController,
    apiViewModel: ApiViewModel = viewModel()
) {

    val uiState = apiViewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "API Screen",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()

                Spacer(modifier = Modifier.height(12.dp))

                Text("Loading health data...")
            }

            uiState.errorMessage != null -> {
                Text(
                    "Error",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(uiState.errorMessage)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        apiViewModel.fetchHealthStats()
                    }
                ) {
                    Text("Try Again")
                }
            }

            uiState.data != null -> {
                val data = uiState.data

                Text(
                    "Global Health Statistics",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Total cases: ${data.cases}")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Total deaths: ${data.deaths}")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Total recovered: ${data.recovered}")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Last updated: ${formatUpdatedTime(data.updated)}")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        apiViewModel.fetchHealthStats()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Refresh")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("summary")
            }
        ) {
            Text("Back")
        }
    }
}

private fun formatUpdatedTime(
    updated: Long
): String {
    val formatter =
        SimpleDateFormat(
            "dd MMM yyyy, HH:mm",
            Locale.getDefault()
        )

    return formatter.format(Date(updated))
}
