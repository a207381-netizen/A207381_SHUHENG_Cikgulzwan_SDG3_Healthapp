package com.example.a207381_cikguizwan_lab01

// ===================== 基础导入 =====================

// Android基础生命周期
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Compose UI相关
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

// Material设计组件
import androidx.compose.material3.*

// 状态管理
import androidx.compose.runtime.*

// UI工具
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

// ViewModel（用于保存数据）
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Navigation（页面跳转核心）
import androidx.navigation.NavController
import androidx.navigation.compose.*


/* ==================== DATA（数据层） ==================== */

// 数据类：用来存储健康数据（目前只有步数）
data class HealthData(
    val steps: String = "3107"   // 默认步数
)


/* ==================== VIEWMODEL（数据管理） ==================== */

// ViewModel用于保存状态（不会因界面刷新丢失）
class HealthViewModel : ViewModel() {

    // mutableStateOf：让UI自动更新
    var data by mutableStateOf(HealthData())
}


/* ==================== MAIN（程序入口） ==================== */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 全屏显示（沉浸式UI）
        enableEdgeToEdge()

        setContent {

            // Material主题（颜色 + 字体）
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFFFFA726),     // 主色（橙色）
                    secondary = Color(0xFFAB47BC),   // 次色（紫色）
                    background = Color(0xFFF5F5F5)   // 背景色
                ),

                typography = Typography(
                    bodyLarge = TextStyle(fontSize = 18.sp),
                    titleLarge = TextStyle(fontSize = 24.sp)
                )
            ) {

                // 进入导航系统
                AppNavigation()
            }
        }
    }
}


/* ==================== NAVIGATION（页面导航） ==================== */

@Composable
fun AppNavigation() {

    // 创建导航控制器（核心）
    val navController = rememberNavController()

    // 获取ViewModel（共享数据）
    val vm: HealthViewModel = viewModel()

    // NavHost：管理所有页面
    NavHost(navController = navController, startDestination = "summary") {

        // 主页面
        composable("summary") {
            MainScreen(navController, vm)
        }

        // Steps页面
        composable("steps") {
            StepsScreen(navController, vm)
        }

        // Highlights页面
        composable("highlights") {
            HighlightsScreen(navController, vm)
        }
    }
}


/* ==================== SUMMARY（主界面） ==================== */

@Composable
fun MainScreen(navController: NavController, vm: HealthViewModel) {

    // 输入框状态
    var inputSteps by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // 顶部Header
        item {
            Header()
        }

        // 输入步数区域
        item {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Enter Steps:")

                TextField(
                    value = inputSteps,
                    onValueChange = { inputSteps = it }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {

                    // 更新ViewModel中的数据
                    if (inputSteps.isNotEmpty()) {
                        vm.data = vm.data.copy(steps = inputSteps)
                    }

                }) {
                    Text("Update Steps")
                }
            }
        }


        // ==================== 【Lab4新增】按钮区域 ====================
        // 👉 用于页面跳转（老师重点看这个）
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                // 跳转到Steps页面
                Button(onClick = {
                    navController.navigate("steps")
                }) {
                    Text("Go Steps")
                }

                // 跳转到Highlights页面
                Button(onClick = {
                    navController.navigate("highlights")
                }) {
                    Text("Go Highlights")
                }
            }
        }
        // ==================== Lab4新增结束 ====================


        // Steps卡片（可点击）
        item {
            CardBox {
                Column(
                    modifier = Modifier
                        .clickable {
                            navController.navigate("steps")
                        }
                        .animateContentSize()
                ) {

                    Text("🔥 Steps", color = Color.Red)

                    Spacer(modifier = Modifier.height(8.dp))

                    // 显示步数（来自ViewModel）
                    Text("${vm.data.steps} steps", fontSize = 26.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    // 柱状图（简单可视化）
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

        // Sleep卡片
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

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}


/* ==================== STEPS SCREEN ==================== */

@Composable
fun StepsScreen(navController: NavController, vm: HealthViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Steps", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))


        // ==================== 【Lab4新增】卡片UI ====================
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Total", color = Color.Gray)

                // 显示步数
                Text("${vm.data.steps} steps", fontSize = 28.sp)

                Spacer(modifier = Modifier.height(16.dp))

                // 原有柱状图（保留）
                Row {
                    Bar(80)
                    Bar(90)
                    Bar(60)
                    Bar(70)
                    Bar(50)
                }
            }
        }
        // ==================== Lab4新增结束 ====================


        Spacer(modifier = Modifier.height(20.dp))

        // 跳转按钮
        Button(onClick = {
            navController.navigate("highlights")
        }) {
            Text("Go to Highlights")
        }
    }
}


/* ==================== HIGHLIGHTS SCREEN ==================== */

@Composable
fun HighlightsScreen(navController: NavController, vm: HealthViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Highlights", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))


        // ==================== 【Lab4新增】Today卡片 ====================
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Today", color = Color.Gray)
                Text("${vm.data.steps} steps", fontSize = 24.sp)
            }
        }


        Spacer(modifier = Modifier.height(12.dp))


        // ==================== 【Lab4新增】Average卡片 ====================
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Average", color = Color.Gray)
                Text("874 steps", fontSize = 24.sp)
            }
        }
        // ==================== Lab4新增结束 ====================


        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.navigate("summary")
        }) {
            Text("Back")
        }
    }
}


/* ==================== UI组件 ==================== */

// 顶部渐变Header
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


// 通用卡片组件
@Composable
fun CardBox(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}


// 柱状图组件
@Composable
fun Bar(height: Int) {
    Box(
        modifier = Modifier
            .width(8.dp)
            .height(height.dp)
            .padding(2.dp)
            .background(
                color = Color(0xFFAB47BC),
                shape = RoundedCornerShape(3.dp)
            )
    )
}