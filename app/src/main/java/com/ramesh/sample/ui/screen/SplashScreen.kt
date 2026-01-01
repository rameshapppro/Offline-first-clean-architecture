package com.ramesh.sample.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    val offsetY by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -1000f,
        animationSpec = tween(durationMillis = 800, delayMillis = 200),
        label = "offsetY"
    )

    val scale by animateFloatAsState(
        targetValue = if (isFinished) 4f else if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isFinished) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "alpha"
    )

    // Animation lifecycle
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1500) // Time text is visible
        isFinished = true
        delay(600) // Wait for zoom/fade out
        onFinished()
    }

    // Ripple effect trigger
    LaunchedEffect(startAnimation, boxSize) {
        if (startAnimation && boxSize != IntSize.Zero) {
            delay(800) // Trigger ripple as text arrives
            val center = Offset(boxSize.width / 2f, boxSize.height / 2f)
            val press = PressInteraction.Press(center)
            interactionSource.emit(press)
            delay(200)
            interactionSource.emit(PressInteraction.Release(press))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onSizeChanged { boxSize = it }
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false, radius = 400.sp.value.dp, color = Color.Green),
                onClick = {}
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Movie",
            modifier = Modifier.graphicsLayer {
                translationY = offsetY
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            },
            style = MaterialTheme.typography.titleLarge,
            fontSize = 78.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onFinished = {})
}
