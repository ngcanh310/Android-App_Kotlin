package com.example.mylife.reuse

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.mylife.R
import com.example.mylife.ui.information.formatDouble

@Composable
fun PieChart(
    consumed: Double,
    target: Double,
    radiusOuter: Dp = dimensionResource(id = R.dimen.radius_outer),
    chartBarWidth: Dp = dimensionResource(id = R.dimen.padding_small),
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
    Box(
        modifier = Modifier
            .size(radiusOuter * 2f)
    ) {
        val consumedColor = colorResource(id = R.color.piechart_consumed)
        val remainColor = colorResource(id = R.color.piechart_remain)

        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawArc(
                color = consumedColor,
                startAngle = -90f,
                sweepAngle = consumedRadius.toFloat(),
                useCenter = false,
                style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
            )
        }

        // Draw remaining arc
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawArc(
                color = remainColor,
                startAngle = (consumedRadius - 90).toFloat(),
                sweepAngle = remainRadius.toFloat(),
                useCenter = false,
                style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
            )
        }

        // Centered Text
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val text2 = "${formatDouble(consumed)}/${formatDouble(target)}"
            val text1 = "Kcal"

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = text1,
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black)
                )

                Text(
                    text = text2,
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black)
                )
            }
        }
    }
}

@Composable
fun SecondarypieChart(
    consumed: Double,
    target: Double,
    nutrition: String,
    radiusOuter: Dp = dimensionResource(id = R.dimen.radius_outer_secondary),
    chartBarWidth: Dp = dimensionResource(id = R.dimen.padding_small),
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

    Box(
        modifier = Modifier
            .size(radiusOuter * 1.5f)
    ) {
        val consumedColor = colorResource(id = R.color.teal_700)
        val remainColor = colorResource(id = R.color.piechart_remain)

        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawArc(
                color = consumedColor,
                startAngle = -90f,
                sweepAngle = consumedRadius.toFloat(),
                useCenter = false,
                style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
            )
        }

        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            drawArc(
                color = remainColor,
                startAngle = (consumedRadius - 90).toFloat(),
                sweepAngle = remainRadius.toFloat(),
                useCenter = false,
                style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val text2 = "${formatDouble(consumed)}/${formatDouble(target)}"
            val text1 = "$nutrition"

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = text1,
                    fontSize = dimensionResource(id = R.dimen.font_tiny).value.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black)
                )

                Text(
                    text = text2,
                    fontSize = dimensionResource(id = R.dimen.font_tiny).value.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black)
                )
            }
        }
    }
}
