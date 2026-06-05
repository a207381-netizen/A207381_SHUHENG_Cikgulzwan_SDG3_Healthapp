package com.example.a207381_cikguizwan_lab01

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun FirestoreScreen(
    navController: NavController,
    firestoreViewModel: FirestoreViewModel = viewModel()
) {

    val uiState = firestoreViewModel.uiState

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {
            Text(
                "Cloud Screen",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = uiState.message,
                onValueChange = {
                    firestoreViewModel.updateMessage(it)
                },
                label = {
                    Text("Health message")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    firestoreViewModel.saveToCloud()
                },
                enabled = !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (uiState.isSaving) {
                        "Saving..."
                    } else {
                        "Save To Cloud"
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            uiState.successMessage?.let { message ->
                Text(message)

                Spacer(modifier = Modifier.height(8.dp))
            }

            uiState.errorMessage?.let { message ->
                Text(message)

                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                "Saved Firestore Records",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        if (uiState.isLoading) {
            item {
                CircularProgressIndicator()

                Spacer(modifier = Modifier.height(12.dp))

                Text("Loading cloud records...")
            }
        } else if (uiState.records.isEmpty()) {
            item {
                Text("No cloud records yet.")
            }
        } else {
            items(uiState.records) { record ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(record.message)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Timestamp: ${record.timestamp}")
                    }
                }
            }
        }

        item {
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
}
