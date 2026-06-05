package com.example.a207381_cikguizwan_lab01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*

data class HealthData(
    val steps: String = "3107"
)

class HealthViewModel : ViewModel() {
    var data by mutableStateOf(HealthData())
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFFFFA726),
                    secondary = Color(0xFFAB47BC),
                    background = Color(0xFFF6F3FF)
                ),

                typography = Typography(
                    bodyLarge = TextStyle(fontSize = 18.sp),
                    titleLarge = TextStyle(fontSize = 26.sp)
                )
            ) {

                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val vm: HealthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "summary"
    ) {

        composable("summary") {
            MainScreen(navController, vm)
        }

        composable("steps") {
            StepsScreen(navController, vm)
        }

        composable("highlights") {
            HighlightsScreen(navController, vm)
        }

        composable("input") {
            InputScreen(navController, vm)
        }

        composable("detail") {
            DetailScreen(navController, vm)
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController,
    vm: HealthViewModel
) {

    var inputSteps by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F3FF))
    ) {

        item {
            Header()
        }

        item {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "Enter Steps",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = inputSteps,
                    onValueChange = {
                        inputSteps = it
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {

                        if (inputSteps.isNotEmpty()) {
                            vm.data = vm.data.copy(
                                steps = inputSteps
                            )
                        }
                    }
                ) {
                    Text("Update Steps")
                }
            }
        }

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(onClick = {
                    navController.navigate("steps")
                }) {
                    Text("Steps")
                }

                Button(onClick = {
                    navController.navigate("highlights")
                }) {
                    Text("Highlights")
                }
            }
        }

        item {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),

                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(onClick = {
                    navController.navigate("input")
                }) {
                    Text("Input")
                }

                Button(onClick = {
                    navController.navigate("detail")
                }) {
                    Text("Detail")
                }
            }
        }

        item {

            CardBox {

                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("steps")
                        }
                        .animateContentSize()
                ) {

                    Text(
                        "🔥 Steps",
                        color = Color.Red,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "${vm.data.steps} steps",
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {

                        Bar(30)
                        Bar(50)
                        Bar(80)
                        Bar(60)
                        Bar(90)
                    }
                }
            }
        }

        item {

            CardBox {

                Column(
                    modifier = Modifier.clickable {
                        navController.navigate("highlights")
                    }
                ) {

                    Text(
                        "🛏 Sleep Score",
                        color = Color.Blue,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "No Data",
                        fontSize = 24.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun StepsScreen(
    navController: NavController,
    vm: HealthViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Steps Screen",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        CardBox {

            Column {

                Text(
                    "Daily Steps",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "${vm.data.steps} steps",
                    fontSize = 32.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Bar(80)
                    Bar(90)
                    Bar(60)
                    Bar(70)
                    Bar(50)
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

@Composable
fun HighlightsScreen(
    navController: NavController,
    vm: HealthViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Highlights",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        CardBox {

            Column {

                Text("Today")

                Spacer(modifier = Modifier.height(8.dp))

                Text("${vm.data.steps} steps")
            }
        }

        CardBox {

            Column {

                Text("Average")

                Spacer(modifier = Modifier.height(8.dp))

                Text("874 steps")
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

@Composable
fun InputScreen(
    navController: NavController,
    vm: HealthViewModel
) {

    var inputSteps by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Input Screen",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = inputSteps,
            onValueChange = {
                inputSteps = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (inputSteps.isNotEmpty()) {
                    vm.data = vm.data.copy(
                        steps = inputSteps
                    )
                }
            }
        ) {
            Text("Save")
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

@Composable
fun DetailScreen(
    navController: NavController,
    vm: HealthViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Detail Screen",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Current Steps",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            vm.data.steps,
            fontSize = 40.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Average: 874 steps",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate("summary")
            }
        ) {
            Text("Back Home")
        }
    }
}

@Composable
fun Header() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFA726),
                        Color(0xFFAB47BC)
                    )
                )
            )
            .padding(20.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Health Summary",
                fontSize = 32.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Track your daily activity",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun CardBox(
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            content()
        }
    }
}

@Composable
fun Bar(height: Int) {

    Box(
        modifier = Modifier
            .padding(end = 6.dp)
            .width(10.dp)
            .height(height.dp)
            .background(
                Color(0xFFAB47BC),
                RoundedCornerShape(4.dp)
            )
    )
}