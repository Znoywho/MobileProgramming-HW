package com.example.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.TextPrimary
import com.example.todo.ui.theme.UthBlue
import com.example.todo.ui.theme.UthBlueLight
import com.example.todo.ui.theme.UthOrange
import com.example.todo.ui.theme.UthOrangeLight

// Khung nền xanh nhạt chứa hình minh hoạ
@Composable
private fun IllustrationFrame(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 190.dp, height = 220.dp)
            .background(UthBlueLight, RoundedCornerShape(24.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun WhiteCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 12.dp)
    ) {
        content()
    }
}

@Composable
private fun GreyLine(width: Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .height(5.dp)
            .background(Color(0xFFDADDE5), CircleShape)
    )
}

@Composable
private fun Chip(text: String, textColor: Color, background: Color) {
    Text(
        text = text,
        color = textColor,
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(background, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

// Bước 1: danh sách việc, 1 việc đã check
@Composable
fun TaskListIllustration() {
    IllustrationFrame {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(true, false, false).forEach { done ->
                WhiteCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (done) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(UthBlue, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(11.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .border(1.5.dp, Color(0xFFB5B9C4), CircleShape)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        GreyLine(100.dp)
                    }
                }
            }
        }
    }
}

// Bước 2: hai nhóm Học tập / Cuộc sống
@Composable
fun CategoryIllustration() {
    IllustrationFrame {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            WhiteCard {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Chip("Học tập", UthBlue, UthBlueLight)
                    GreyLine(120.dp)
                }
            }
            WhiteCard {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Chip("Cuộc sống", UthOrange, UthOrangeLight)
                    GreyLine(120.dp)
                }
            }
        }
    }
}

// Bước 3: chuông nhắc việc
@Composable
fun ReminderIllustration() {
    IllustrationFrame {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .shadow(2.dp, CircleShape)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = UthBlue,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Ôn bài Toán lúc 19:00",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier
                    .shadow(2.dp, RoundedCornerShape(8.dp))
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}
