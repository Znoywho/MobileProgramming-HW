package  com.example.todo

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo.ui.theme.TextPrimary
import com.example.todo.ui.theme.TextSecondary
import com.example.todo.ui.theme.UthBlue
import com.example.todo.ui.theme.UthBlueSoft

// Màn hình chờ: nền xanh, logo lớn, thanh tiến trình rồi tự chuyển sang onboarding
@Composable
fun SplashScreen(navController: NavController) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(1f, tween(durationMillis = 10000, easing = LinearEasing))
        navController.navigate(Destination.Onboarding1) {
            popUpTo(Destination.Splash) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UthBlue)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo lớn (white tile)
            Image(
                painter = painterResource(R.drawable.gemini_logo),
                contentDescription = "UTH Todo logo",
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = "UTH Todo",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Việc học, việc nhà — gọn gàng mỗi ngày",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(3.dp)
                    .background(Color.White.copy(alpha = 0.3f), CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp * progress.value)
                        .height(3.dp)
                        .background(Color.White, CircleShape)
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Phiên bản 2.1.0",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 11.sp
            )
        }
    }
}

// "UTH" đậm + "Todo" màu xanh, kèm logo nhỏ
@Composable
fun BrandTitle() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.uth_logo_small),
            contentDescription = "UTH Todo logo",
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = TextPrimary, fontWeight = FontWeight.Bold)) {
                    append("UTH ")
                }
                withStyle(SpanStyle(color = UthBlue, fontWeight = FontWeight.Medium)) {
                    append("Todo")
                }
            },
            fontSize = 17.sp
        )
    }
}

// Chấm chỉ trang: chấm hiện tại dài và xanh
@Composable
fun PageIndicator(count: Int, current: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        repeat(count) { i ->
            val width by animateDpAsState(if (i == current) 18.dp else 6.dp, label = "dot")
            Box(
                modifier = Modifier
                    .width(width)
                    .height(6.dp)
                    .background(if (i == current) UthBlue else UthBlueSoft, CircleShape)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(
    navController: NavController,
    page: OnboardingPage
) {
    val goHome = {
        navController.navigate(Destination.Home) {
            popUpTo(Destination.Onboarding1) { inclusive = true }
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { BrandTitle() },
                actions = {
                    TextButton(onClick = goHome) {
                        Text("Bỏ qua", color = TextSecondary, fontSize = 14.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                page.illustration()
            }

            Text(
                text = page.title,
                fontSize = 24.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = page.description,
                fontSize = 15.sp,
                lineHeight = 21.sp,
                color = TextSecondary
            )
            Spacer(Modifier.height(28.dp))
            PageIndicator(count = OnboardingPage.all.size, current = page.index)
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (page.next == Destination.Home) goHome()
                    else navController.navigate(page.next)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = UthBlue)
            ) {
                Text(page.buttonText, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { BrandTitle() },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Home Screen", fontSize = 24.sp, color = TextPrimary)
        }
    }
}
