package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.LossRedBright
import com.example.ui.theme.ProfitGreenBright

@Composable
fun MiniSparkline(
  points: List<Float>,
  isBullish: Boolean,
  modifier: Modifier = Modifier,
  strokeWidth: Float = 4.5f
) {
  val lineColor = if (isBullish) ProfitGreenBright else LossRedBright
  val fillColor = if (isBullish) Color(0x3300C853) else Color(0x33FF5630)

  Canvas(modifier = modifier.fillMaxSize()) {
    if (points.size < 2) return@Canvas

    val width = size.width
    val height = size.height
    val padding = 4.dp.toPx()
    val availableHeight = height - (padding * 2)

    val minVal = points.minOrNull() ?: 0f
    val maxVal = points.maxOrNull() ?: 1f
    val range = if (maxVal - minVal == 0f) 1f else maxVal - minVal

    val stepX = width / (points.size - 1)
    val path = Path()
    val fillPath = Path()

    points.forEachIndexed { index, point ->
      val x = index * stepX
      val normalizedY = (point - minVal) / range
      val y = (height - padding) - (normalizedY * availableHeight)

      if (index == 0) {
        path.moveTo(x, y)
        fillPath.moveTo(x, height)
        fillPath.lineTo(x, y)
      } else {
        // Cubic bezier smoothing
        val prevX = (index - 1) * stepX
        val prevNormalizedY = (points[index - 1] - minVal) / range
        val prevY = (height - padding) - (prevNormalizedY * availableHeight)

        val controlPoint1 = Offset(prevX + stepX / 2f, prevY)
        val controlPoint2 = Offset(prevX + stepX / 2f, y)

        path.cubicTo(
          controlPoint1.x, controlPoint1.y,
          controlPoint2.x, controlPoint2.y,
          x, y
        )
        fillPath.cubicTo(
          controlPoint1.x, controlPoint1.y,
          controlPoint2.x, controlPoint2.y,
          x, y
        )
      }
    }

    // Close gradient fill path
    fillPath.lineTo(width, height)
    fillPath.close()

    // Draw gradient area underneath
    drawPath(
      path = fillPath,
      brush = Brush.verticalGradient(
        colors = listOf(fillColor, Color.Transparent),
        startY = 0f,
        endY = height
      )
    )

    // Draw the sparkline stroke
    drawPath(
      path = path,
      color = lineColor,
      style = Stroke(
        width = strokeWidth,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )

    // Draw active glowing endpoint
    val lastNormalizedY = (points.last() - minVal) / range
    val lastY = (height - padding) - (lastNormalizedY * availableHeight)
    drawCircle(
      color = lineColor.copy(alpha = 0.4f),
      radius = 7.dp.toPx(),
      center = Offset(width, lastY)
    )
    drawCircle(
      color = lineColor,
      radius = 3.5.dp.toPx(),
      center = Offset(width, lastY)
    )
  }
}
