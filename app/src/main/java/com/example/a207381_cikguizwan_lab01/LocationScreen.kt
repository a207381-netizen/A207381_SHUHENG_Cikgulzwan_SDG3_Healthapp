package com.example.a207381_cikguizwan_lab01

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@Composable
fun LocationScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var latitude by remember {
        mutableStateOf("Not available")
    }

    var longitude by remember {
        mutableStateOf("Not available")
    }

    var statusMessage by remember {
        mutableStateOf("Tap the button to get your current location.")
    }

    fun hasLocationPermission(): Boolean {
        val fineLocationGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        return fineLocationGranted || coarseLocationGranted
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        if (!hasLocationPermission()) {
            statusMessage = "Location permission is required."
            return
        }

        statusMessage = "Getting current location..."

        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient
            .getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )
            .addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    statusMessage = "Location updated."
                } else {
                    statusMessage = "Unable to get current location."
                }
            }
            .addOnFailureListener {
                statusMessage = "Failed to get current location."
            }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val fineLocationGranted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

            val coarseLocationGranted =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (fineLocationGranted || coarseLocationGranted) {
                getCurrentLocation()
            } else {
                statusMessage = "Location permission denied."
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Location Screen",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Latitude",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            latitude,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Longitude",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            longitude,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(statusMessage)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (hasLocationPermission()) {
                    getCurrentLocation()
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Current Location")
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
