package com.example.myapplication.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.myapplication.data.Product
import java.util.Locale

// ---- Bảng màu ----
private val Blue = Color(0xFF2F80ED)
private val BlueDeep = Color(0xFF1C5FD4)
private val Ink = Color(0xFF1A1D29)
private val InkSoft = Color(0xFF5B6275)
private val PriceRed = Color(0xFFE53935)
private val BgTop = Color(0xFFEAF2FF)
private val BgBottom = Color(0xFFF8FAFD)
private val HeroStart = Color(0xFFFF6B4A)
private val HeroEnd = Color(0xFFE8341C)

@Composable
fun ProductDetailScreen(
    onBack: () -> Unit,
    vm: ProductViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BgTop, BgBottom)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            TopBar(onBack = onBack, onRefresh = vm::loadProduct)

            AnimatedContent(
                targetState = vm.uiState,
                transitionSpec = {
                    (fadeIn() + slideInVertically { it / 12 }) togetherWith fadeOut()
                },
                contentKey = { it::class },
                label = "productState"
            ) { state ->
                when (state) {
                    is ProductUiState.Loading -> LoadingView()
                    is ProductUiState.Error -> ErrorView(state.message, vm::loadProduct)
                    is ProductUiState.Success -> ProductContent(state.product)
                }
            }
        }
    }
}

// ---------------- Top bar ----------------

@Composable
private fun TopBar(onBack: () -> Unit, onRefresh: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // Nút back: gradient xanh + đổ bóng xanh
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(44.dp)
                .shadow(12.dp, RoundedCornerShape(14.dp), spotColor = Blue, ambientColor = Blue)
                .clip(RoundedCornerShape(14.dp))
                .background(Brush.linearGradient(listOf(Blue, BlueDeep)))
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.size(18.dp)) {
                val path = Path().apply {
                    moveTo(size.width * 0.65f, size.height * 0.15f)
                    lineTo(size.width * 0.30f, size.height * 0.50f)
                    lineTo(size.width * 0.65f, size.height * 0.85f)
                }
                drawPath(
                    path, Color.White,
                    style = Stroke(width = 2.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                )
            }
        }

        Text(
            text = "Product detail",
            modifier = Modifier.align(Alignment.Center),
            color = Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // Nút tải lại (API mock trả về dữ liệu ngẫu nhiên mỗi lần gọi)
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(44.dp)
                .shadow(10.dp, RoundedCornerShape(14.dp), spotColor = Blue.copy(alpha = 0.5f))
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable(onClick = onRefresh),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.size(20.dp)) {
                val stroke = 2.4.dp.toPx()
                drawArc(
                    color = Blue, startAngle = -60f, sweepAngle = 290f, useCenter = false,
                    topLeft = Offset(stroke, stroke),
                    size = androidx.compose.ui.geometry.Size(size.width - stroke * 2, size.height - stroke * 2),
                    style = Stroke(width = stroke, cap = StrokeCap.Round)
                )
                val tip = Path().apply {
                    moveTo(size.width * 0.62f, size.height * 0.02f)
                    lineTo(size.width * 0.95f, size.height * 0.10f)
                    lineTo(size.width * 0.80f, size.height * 0.40f)
                    close()
                }
                drawPath(tip, Blue)
            }
        }
    }
}

// ---------------- Nội dung sản phẩm ----------------

@Composable
private fun ProductContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        // Ảnh sản phẩm: nền gradient cam-đỏ, bo góc lớn, đổ bóng màu
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(28.dp),
                    spotColor = HeroEnd,
                    ambientColor = HeroEnd
                )
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.linearGradient(listOf(HeroStart, HeroEnd)))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(product.imgURL)
                    .crossfade(true)
                    .build(),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // lớp phủ mờ dưới đáy cho ảnh có chiều sâu
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0.6f to Color.Transparent,
                            1f to Color.Black.copy(alpha = 0.25f)
                        )
                    )
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = product.name,
            color = Ink,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 28.sp
        )

        Spacer(Modifier.height(12.dp))

        // Giá: pill đỏ có đổ bóng nhẹ
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Giá:", color = InkSoft, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .shadow(8.dp, CircleShape, spotColor = PriceRed, ambientColor = PriceRed)
                    .clip(CircleShape)
                    .background(Brush.horizontalGradient(listOf(Color(0xFFFF5252), PriceRed)))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = formatPrice(product.price),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Mô tả: card trắng đổ bóng
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(22.dp), spotColor = Blue.copy(alpha = 0.35f))
                .clip(RoundedCornerShape(22.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(width = 4.dp, height = 18.dp)
                        .clip(CircleShape)
                        .background(Blue)
                )
                Spacer(Modifier.width(10.dp))
                Text("Mô tả sản phẩm", color = Ink, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = product.des,
                color = InkSoft,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}

private fun formatPrice(price: Double): String =
    "$" + String.format(Locale.US, "%,.2f", price)

// ---------------- Trạng thái loading / lỗi ----------------

@Composable
private fun LoadingView() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Blue, strokeWidth = 4.dp)
            Spacer(Modifier.height(16.dp))
            Text("Đang tải sản phẩm...", color = InkSoft)
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(22.dp), spotColor = PriceRed.copy(alpha = 0.4f))
                .clip(RoundedCornerShape(22.dp))
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Không tải được dữ liệu", color = Ink, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(message, color = InkSoft, textAlign = TextAlign.Center, fontSize = 14.sp)
            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) {
                Text("Thử lại", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
