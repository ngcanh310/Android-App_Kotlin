package com.example.mylife.reuse

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mylife.ui.information.formatDouble

@Composable
fun PieChart(
    consumed: Double,
    target: Double,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 10.dp,
    animDuration: Int = 1000,
) {
    val totalSum = target
    val consumedRatio = consumed / totalSum
    val consumedRadius = if (consumedRatio > 1.0) 360.0 else 360 * consumedRatio
    val remainRadius = 360 - consumedRadius

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 1f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Canvas(
        modifier = Modifier
            .size(radiusOuter * 2f)
            .rotate(animateRotation)
    ) {
        val consumedColor = Color(19, 6, 143)
        val remainColor = Color(214, 209, 230)

        drawArc(
            color = consumedColor,
            startAngle = -90f,
            sweepAngle = consumedRadius.toFloat(),
            useCenter = false,
            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
        )

        lastValue += consumedRadius.toFloat()

        drawArc(
            color = remainColor,
            startAngle = lastValue - 90f,
            sweepAngle = remainRadius.toFloat(),
            useCenter = false,
            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
        )

        drawIntoCanvas { canvas ->
            val text1 = "${formatDouble(consumed)}/${formatDouble(target)}"
            val text2 = "Kcal"

            val textPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 60f
                textAlign = android.graphics.Paint.Align.CENTER
            }

            canvas.nativeCanvas.rotate(-animateRotation, size.width / 2f, size.height / 2f)

            val text1Height = textPaint.descent() + textPaint.ascent()

            canvas.nativeCanvas.drawText(
                text1,
                size.width / 2f,
                size.height / 2f - text1Height / 2,
                textPaint
            )

            canvas.nativeCanvas.drawText(
                text2,
                size.width / 2f,
                size.height / 2f + text1Height,
                textPaint
            )
        }
    }
}

@Composable
fun SecondarypieChart(
    consumed: Double,
    target: Double,
    nutrition: String,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 10.dp,
    animDuration: Int = 1000,
) {

    val totalSum = target
    val consumedRatio = consumed / totalSum
    val consumedRadius = if (consumedRatio > 1.0) 360.0 else 360 * consumedRatio
    val remainRadius = 360 - consumedRadius

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 1f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Canvas(
        modifier = Modifier
            .size(radiusOuter * 1.3f)
            .rotate(animateRotation)
    ) {
        val consumedColor = Color(0xFF800080)
        val remainColor = Color(214, 209, 230)

        drawArc(
            color = consumedColor,
            startAngle = -90f,
            sweepAngle = consumedRadius.toFloat(),
            useCenter = false,
            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
        )

        lastValue += consumedRadius.toFloat()

        drawArc(
            color = remainColor,
            startAngle = lastValue - 90f,
            sweepAngle = remainRadius.toFloat(),
            useCenter = false,
            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
        )

        drawIntoCanvas { canvas ->
            val text1 = "${formatDouble(consumed)}/${formatDouble(target)}"
            val text2 = "$nutrition"

            val textPaint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 60f
                textAlign = android.graphics.Paint.Align.CENTER
            }

            canvas.nativeCanvas.rotate(-animateRotation, size.width / 2f, size.height / 2f)

            val text1Height = textPaint.descent() + textPaint.ascent()

            canvas.nativeCanvas.drawText(
                text1,
                size.width / 2f,
                size.height / 2f - text1Height / 2,
                textPaint
            )

            canvas.nativeCanvas.drawText(
                text2,
                size.width / 2f,
                size.height / 2f + text1Height,
                textPaint
            )
        }
    }
}
