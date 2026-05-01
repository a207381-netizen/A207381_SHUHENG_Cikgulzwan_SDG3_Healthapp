package com.example.a207381_cikguizwan_lab01

// ===================== 基础导入 =====================
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

import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController
import androidx.navigation.compose.*


// ==================== DATA ====================
data class HealthData(
    val steps: String = "3107"
)


// ==================== VIEWMODEL ====================
class HealthViewModel : ViewModel() {
    var data by mutableStateOf(HealthData())
}


// ==================== MAIN ====================
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFFFFA726),
                    secondary = Color(0xFFAB47BC),
                    background = Color(0xFFF5F5F5)
                ),
                typography = Typography(
                    bodyLarge = TextStyle(fontSize = 18.sp),
                    titleLarge = TextStyle(fontSize = 24.sp)
                )
            ) {
                AppNavigation()
            }
        }
    }
}


// ==================== NAVIGATION ====================
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val vm: HealthViewModel = viewModel()

    NavHost(navController = navController, startDestination = "summary") {

        composable("summary") {
            MainScreen(navController, vm)
        }

        composable("steps") {
            StepsScreen(navController, vm)
        }

        composable("highlights") {
            HighlightsScreen(navController, vm)
        }

        // ==================== 【Project新增】页面 ====================
        composable("input") {
            InputScreen(navController, vm)
        }

        composable("detail") {
            DetailScreen(navController, vm)
        }
        // ==================== 新增结束 ====================
    }
}


// ==================== MAIN SCREEN ====================
@Composable
fun MainScreen(navController: NavController, vm: HealthViewModel) {

    var inputSteps by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        item { Header() }

        item {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Enter Steps:")

                TextField(
                    value = inputSteps,
                    onValueChange = { inputSteps = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    if (inputSteps.isNotEmpty()) {
                        vm.data = vm.data.copy(steps = inputSteps)
                    }
                }) {
                    Text("Update Steps")
                }
            }
        }

        // ==================== Lab4按钮 ====================
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { navController.navigate("steps") }) {
                    Text("Go Steps")
                }

                Button(onClick = { navController.navigate("highlights") }) {
                    Text("Go Highlights")
                }
            }
        }

        // ==================== 【Project新增】按钮 ====================
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(onClick = {
                    navController.navigate("input")
                }) {
                    Text("Input Page")
                }

                Button(onClick = {
                    navController.navigate("detail")
                }) {
                    Text("Detail Page")
                }
            }
        }
        // ==================== 新增结束 ====================


        item {
            CardBox {
                Column(
                    modifier = Modifier
                        .clickable { navController.navigate("steps") }
                        .animateContentSize()
                ) {
                    Text("🔥 Steps", color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${vm.data.steps} steps", fontSize = 26.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Bar(30); Bar(50); Bar(80); Bar(60); Bar(90)
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
                    Text("🛏 Sleep Score", color = Color.Blue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No Data", fontSize = 22.sp)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}


// ==================== STEPS ====================
@Composable
fun StepsScreen(navController: NavController, vm: HealthViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Steps", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total", color = Color.Gray)
                Text("${vm.data.steps} steps", fontSize = 28.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row { Bar(80); Bar(90); Bar(60); Bar(70); Bar(50) }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("highlights")
        }) {
            Text("Go to Highlights")
        }
    }
}


// ==================== HIGHLIGHTS ====================
@Composable
fun HighlightsScreen(navController: NavController, vm: HealthViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Highlights", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Today")
                Text("${vm.data.steps} steps")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Average")
                Text("874 steps")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("summary")
        }) {
            Text("Back")
        }
    }
}


// ==================== 【Project新增】INPUT ====================
@Composable
fun InputScreen(navController: NavController, vm: HealthViewModel) {

    var inputSteps by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Input Steps", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = inputSteps,
            onValueChange = { inputSteps = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (inputSteps.isNotEmpty()) {
                vm.data = vm.data.copy(steps = inputSteps)
            }
        }) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("summary")
        }) {
            Text("Back")
        }
    }
}
// ==================== 新增结束 ====================


// ==================== 【Project新增】DETAIL ====================
@Composable
fun DetailScreen(navController: NavController, vm: HealthViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Detail Screen", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Steps: ${vm.data.steps}", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Text("Average: 874 steps", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("summary")
        }) {
            Text("Back")
        }
    }
}
// ==================== 新增结束 ====================


// ==================== UI组件 ====================
@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFFFFA726), Color(0xFFAB47BC))
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Text("Summary", fontSize = 28.sp)
            Text("Pinned")
        }
    }
}

@Composable
fun CardBox(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.padding(12.dp).fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun Bar(height: Int) {
    Box(
        modifier = Modifier
            .width(8.dp)
            .height(height.dp)
            .padding(2.dp)
            .background(Color(0xFFAB47BC), RoundedCornerShape(3.dp))
    )
}