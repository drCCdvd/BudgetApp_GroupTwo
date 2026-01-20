package com.example.budgetapp_grouptwo.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


private val LightBlue = Color(0xFFBFD6FF)

@Composable
fun QuickActionFab(
    isOpen: Boolean,
    onToggle: () -> Unit,
    onDismiss: () -> Unit,
    onAddTransaction: () -> Unit,
    onCreateGoal: () -> Unit,
    onDepositToGoal: () -> Unit

) {
    BackHandler(enabled = isOpen) {
        onDismiss()
    }
    val progress by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        label = "quickMenuProgress"
    )
    val rotation by animateFloatAsState(
        targetValue = if (isOpen) -45f else 0f,
        label = "fabRotation"
    )
    val overlayProgress by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        label = "overlayProgress"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //Overlay
        if (overlayProgress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(1f, 1f)
                        scaleX = overlayProgress
                        scaleY = overlayProgress
                        alpha = 0.45f * overlayProgress
                    }
                    .background(Color.Black)
                    .pointerInput(isOpen) {
                        detectTapGestures { onDismiss() }
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
                .padding(160.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            RadialAction(
                progress = progress,
                offsetX = 0.dp,
                offsetY = (-220).dp,
                icon = Icons.Filled.Flag,
                containerColor = Color.White,
                iconTint = Color.Black,
                labelText = "Mål",
                onClick = { onDismiss(); onCreateGoal() }
            )
            RadialAction(
                progress = progress,
                offsetX = 0.dp,
                offsetY = (-90).dp,
                icon = Icons.Filled.SwapHoriz,
                containerColor = Color.White,
                iconTint = Color.Black,
                labelText = "Transaktion",
                onClick = { onDismiss(); onAddTransaction() }
            )

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(3f)
                .padding(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // +
            FloatingActionButton(
                onClick = onToggle,
                shape = CircleShape
            ) {

                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = if (isOpen) "Luk menu" else "Åbn menu",
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    }
}




@Composable
private fun RadialAction(
    progress: Float,
    offsetX: Dp,
    offsetY: Dp,
    icon:ImageVector,
    containerColor: Color,
    iconTint: Color,
    labelText: String,
    onClick: () -> Unit
) {
    if (progress <= 0f) return

    Column(
        modifier = Modifier
            .offset(
                x = offsetX * progress,
                y = offsetY * progress
            )
            .scale(progress.coerceIn(0f, 1f)),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = containerColor,
            tonalElevation = 6.dp,
            shadowElevation = 2.dp,
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = labelText,
                    tint = iconTint,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Text(
            text = labelText,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .widthIn(min = 72.dp)
        )
    }
}