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
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

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
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    var inputSteps by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("3107") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                    .padding(16.dp)
            ) {

                Column {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("16:29")
                        Text("📶 📡 🔋")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Summary", fontSize = 28.sp)
                        Text("👤")
                    }

                    Text("Pinned")
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Enter Steps:", fontSize = 16.sp)

                TextField(
                    value = inputSteps,
                    onValueChange = { inputSteps = it },
                    placeholder = { Text("e.g. 5000") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    if (inputSteps.isNotEmpty()) {
                        steps = inputSteps
                    }
                }) {
                    Text("Update Steps")
                }
            }
        }

        item {

            var expanded by remember { mutableStateOf(false) }

            CardBox {
                Column(
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .animateContentSize()
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔥 Steps", color = Color.Red)
                        Text("Today")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Text("$steps steps", fontSize = 26.sp)

                        Row(verticalAlignment = Alignment.Bottom) {
                            Bar(30)
                            Bar(50)
                            Bar(80)
                            Bar(60)
                            Bar(90)
                        }
                    }

                    if (expanded) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Steps Goal: 8000")
                        Text("Calories: 320 kcal")
                    }
                }
            }
        }

        item {
            CardBox {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🛏 Sleep Score", color = Color.Blue)
                        Text("Today")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No Data", fontSize = 22.sp)
                }
            }
        }

        item {
            SimpleItem("❤️ Show All Health Data")
        }

        item {
            SectionTitle("Trends")
            SimpleItem("📊 Show All Health Trends")
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ⭐ 绿色卡片
@Composable
fun CardBox(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2196F3) // ⭐ 浅绿色
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

// ⭐ 绿色小卡片
@Composable
fun SimpleItem(text: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        )
    ) {
        Text(
            text,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        fontSize = 22.sp,
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun Bar(height: Int) {
    Box(
        modifier = Modifier
            .width(6.dp)
            .height(height.dp)
            .padding(horizontal = 2.dp)
            .background(
                Color(0xFFAB47BC),
                RoundedCornerShape(3.dp)
            )
    )
}