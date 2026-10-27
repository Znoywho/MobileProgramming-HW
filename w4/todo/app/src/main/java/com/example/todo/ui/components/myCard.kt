package com.example.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Wraps any composable with a solid "shadow" layer offset behind it.
@Composable
fun myCard(
    modifier: Modifier = Modifier,
    shadowOffsetX: Dp = 6.dp,
    shadowOffsetY: Dp = 6.dp,
    shadowColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.onSurface,
    shape: Shape = RoundedCornerShape(12.dp),
    composable: @Composable () -> Unit,
) {
    // Reserve room so the offset shadow isn't clipped by neighbours
    Box(modifier = modifier.padding(end = shadowOffsetX, bottom = shadowOffsetY)) {
        // Shadow: same size as the content, shifted down-right
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffsetX, y = shadowOffsetY)
                .background(shadowColor, shape)
        )
        // Front layer
        Box(
            modifier = Modifier
                .background(backgroundColor, shape)
                .border(2.dp, borderColor, shape)
        ) {
            composable()
        }
    }
}
