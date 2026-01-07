package com.example.budgetapp_grouptwo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun QuickActionFab(
    isOpen: Boolean,
    onToggle: () -> Unit,
    onDismiss: () -> Unit,
    onAddTransaction: () -> Unit,
    onCreateGoal: () -> Unit,
    onDepositToGoal: () -> Unit,
    radius: Dp = 120.dp
) {
    val progress by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        label = "quickMenuProgress"
    )

    val radiusPx = with(LocalDensity.current) { radius.toPx() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        //Overlay
        AnimatedVisibility(visible = isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
                    .clickable { onDismiss() }
            )
        }
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            RadialChip(
                visible = isOpen,
                progress = progress,
                angleDegrees = 225f,
                radiusPx = radiusPx,
                icon = Icons.Filled.ReceiptLong,
                labelText = "Tilføj transaktion",
                onClick = { onDismiss(); onAddTransaction() }
            )
            RadialChip(
                visible = isOpen,
                progress = progress,
                angleDegrees = 270f,
                radiusPx = radiusPx,
                icon = Icons.Filled.Flag,
                labelText = "Opret mål",
                onClick = { onDismiss(); onCreateGoal() }
            )
            RadialChip(
                visible = isOpen,
                progress = progress,
                angleDegrees = 315f,
                radiusPx = radiusPx,
                icon = Icons.Filled.ArrowUpward,
                labelText = "Overfør til mål",
                onClick = { onDismiss(); onDepositToGoal() }
            )
            // +
            FloatingActionButton(
                onClick = onToggle,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Åbn/luk menu")
            }
        }
    }
}
@Composable
private fun RadialChip(
    visible: Boolean,
    progress: Float,
    angleDegrees: Float,
    radiusPx: Float,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    labelText: String,
    onClick: () -> Unit
) {
    if (!visible) return

    val angleRad = Math.toRadians(angleDegrees.toDouble())
    val x = (cos(angleRad) * radiusPx * progress).toFloat()
    val y = (sin(angleRad) * radiusPx * progress).toFloat()

    Box(
        modifier = Modifier
            .offset(
                x = with(LocalDensity.current) { x.toDp() },
                y = with(LocalDensity.current) { y.toDp() }
            )
                .scale(progress.coerceIn(0f, 1f))
    ) {
        AssistChip(
            onClick = onClick,
            label = { Text(labelText) },
            leadingIcon = { Icon(icon, contentDescription = labelText) }
        )
    }
}