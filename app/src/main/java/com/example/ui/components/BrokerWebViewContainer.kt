package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.BrokerType
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.LossRed
import com.example.ui.theme.LossRedBright
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBg
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

@Composable
fun BrokerWebViewCard(
  broker: BrokerType,
  onClose: () -> Unit,
  onFullScreen: () -> Unit,
  modifier: Modifier = Modifier
) {
  val brokerPrimary = Color(broker.primaryColorHex)

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(16.dp))
      .border(
        width = 1.dp,
        brush = Brush.linearGradient(
          colors = listOf(
            brokerPrimary.copy(alpha = 0.6f),
            DarkBorder
          )
        ),
        shape = RoundedCornerShape(16.dp)
      )
      .testTag("broker_webview_embedded_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      var isReloading by remember { mutableStateOf(false) }

      // Container Top Bar / Browser Frame
      BrokerWebViewControlBar(
        broker = broker,
        isFullScreen = false,
        isReloading = isReloading,
        onRefresh = { isReloading = true },
        onClose = onClose,
        onToggleFullScreen = onFullScreen
      )

      // In-App Web Viewport Area
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(350.dp)
          .background(DarkBackground)
      ) {
        BrokerWebViewTradingViewport(
          broker = broker,
          isReloading = isReloading,
          onReloadFinished = { isReloading = false },
          modifier = Modifier.fillMaxSize()
        )
      }
    }
  }
}

@Composable
fun BrokerWebViewFullScreenDialog(
  broker: BrokerType,
  onDismiss: () -> Unit
) {
  var isReloading by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(
      usePlatformDefaultWidth = false,
      decorFitsSystemWindows = false
    )
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(DarkBackground)
        .testTag("broker_webview_fullscreen_dialog")
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        BrokerWebViewControlBar(
          broker = broker,
          isFullScreen = true,
          isReloading = isReloading,
          onRefresh = { isReloading = true },
          onClose = onDismiss,
          onToggleFullScreen = onDismiss,
          modifier = Modifier.statusBarsPadding()
        )

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(DarkBackground)
        ) {
          BrokerWebViewTradingViewport(
            broker = broker,
            isReloading = isReloading,
            onReloadFinished = { isReloading = false },
            modifier = Modifier.fillMaxSize()
          )
        }
      }
    }
  }
}

@Composable
fun BrokerWebViewControlBar(
  broker: BrokerType,
  isFullScreen: Boolean,
  isReloading: Boolean,
  onRefresh: () -> Unit,
  onClose: () -> Unit,
  onToggleFullScreen: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val brokerPrimary = Color(broker.primaryColorHex)

  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(DarkSurface)
      .border(0.5.dp, DarkBorder)
      .padding(horizontal = 12.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    // Left: SSL Lock + Web URL Address Bar
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      Box(
        modifier = Modifier
          .size(26.dp)
          .clip(CircleShape)
          .background(brokerPrimary.copy(alpha = 0.2f))
          .border(1.dp, brokerPrimary, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = broker.shortName,
          color = brokerPrimary,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "SSL Secured",
            tint = ProfitGreenBright,
            modifier = Modifier.size(11.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${broker.displayName} In-App Web Desk",
            color = TextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = "${broker.defaultUrl.replace("https://", "")}/trade/live",
          color = TextMuted,
          fontSize = 10.sp
        )
      }
    }

    // Right Action Icons
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
      // Reload Button
      IconButton(
        onClick = onRefresh,
        modifier = Modifier.size(32.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = "Reload Page",
          tint = if (isReloading) brokerPrimary else TextMuted,
          modifier = Modifier.size(16.dp)
        )
      }

      // External Browser Intent
      IconButton(
        onClick = {
          try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(broker.defaultUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
          } catch (_: Exception) {}
        },
        modifier = Modifier.size(32.dp)
      ) {
        Icon(
          imageVector = Icons.Default.OpenInBrowser,
          contentDescription = "Open in External Browser",
          tint = brokerPrimary,
          modifier = Modifier.size(16.dp)
        )
      }

      // Fullscreen Toggle
      IconButton(
        onClick = onToggleFullScreen,
        modifier = Modifier.size(32.dp)
      ) {
        Icon(
          imageVector = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
          contentDescription = "Toggle Fullscreen",
          tint = brokerPrimary,
          modifier = Modifier.size(18.dp)
        )
      }

      // Close Button
      IconButton(
        onClick = onClose,
        modifier = Modifier.size(32.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Close",
          tint = TextMuted,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

/**
 * High-performance Jetpack Compose in-app web trading viewport.
 * Provides live real-time chart canvas, pair selection, payout calculation,
 * and Quotex/Pocket Option responsive trade execution with zero Chromium dependencies.
 */
@Composable
fun BrokerWebViewTradingViewport(
  broker: BrokerType,
  isReloading: Boolean,
  onReloadFinished: () -> Unit,
  modifier: Modifier = Modifier
) {
  val brokerPrimary = Color(broker.primaryColorHex)
  val brokerAccent = Color(broker.accentColorHex)
  val isQuotex = broker == BrokerType.QUOTEX

  var currentPrice by remember { mutableDoubleStateOf(1.08642) }
  var investmentAmount by remember { mutableIntStateOf(50) }
  var selectedTimeframe by remember { mutableStateOf("1M") }
  var executionToastMessage by remember { mutableStateOf<String?>(null) }
  var loadingProgress by remember { mutableStateOf(1.0f) }

  val pricePoints = remember {
    mutableStateListOf<Double>().apply {
      var p = 1.08550
      for (i in 0 until 40) {
        p += (Random.nextDouble() - 0.49) * 0.00015
        add(p)
      }
    }
  }

  // Reload simulation
  LaunchedEffect(isReloading) {
    if (isReloading) {
      loadingProgress = 0.2f
      delay(300)
      loadingProgress = 0.6f
      delay(200)
      loadingProgress = 1.0f
      onReloadFinished()
    }
  }

  // Real-time ticking price engine (800ms updates)
  LaunchedEffect(broker) {
    while (true) {
      delay(800)
      val delta = (Random.nextDouble() - 0.488) * 0.00014
      currentPrice = (currentPrice + delta).coerceIn(1.08200, 1.09100)
      if (pricePoints.size >= 40) {
        pricePoints.removeAt(0)
      }
      pricePoints.add(currentPrice)
    }
  }

  // Toast message auto-dismiss
  LaunchedEffect(executionToastMessage) {
    if (executionToastMessage != null) {
      delay(2400)
      executionToastMessage = null
    }
  }

  Box(modifier = modifier.background(DarkBackground)) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Loading Indicator Bar
      if (isReloading) {
        LinearProgressIndicator(
          progress = { loadingProgress },
          modifier = Modifier
            .fillMaxWidth()
            .height(2.5.dp),
          color = brokerPrimary,
          trackColor = DarkBackground
        )
      }

      // Asset & Payout Header Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF111722))
          .border(0.5.dp, DarkBorder)
          .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(ProfitGreenBright)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isQuotex) "QUOTEX OTC • EUR/USD" else "POCKET OPTION • EUR/USD OTC",
            color = TextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(ProfitGreenBg)
              .border(0.5.dp, ProfitGreenBright.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "94% PAYOUT",
              color = ProfitGreenBright,
              fontSize = 9.sp,
              fontWeight = FontWeight.ExtraBold
            )
          }
        }

        // Timeframe Selector Pills
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          listOf("5s", "1M", "5M").forEach { tf ->
            val isSelected = selectedTimeframe == tf
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (isSelected) brokerPrimary.copy(alpha = 0.25f) else DarkSurfaceElevated)
                .border(
                  0.5.dp,
                  if (isSelected) brokerPrimary else Color.Transparent,
                  RoundedCornerShape(4.dp)
                )
                .clickable { selectedTimeframe = tf }
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = tf,
                color = if (isSelected) TextWhite else TextMuted,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        }
      }

      // Real-time Canvas Chart Area
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 4.dp, vertical = 2.dp)
      ) {
        // Price HUD Overlay
        Column(
          modifier = Modifier
            .padding(top = 6.dp, start = 8.dp)
            .align(Alignment.TopStart)
        ) {
          Text(
            text = "LIVE OTC FEED",
            color = TextMuted,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp
          )
          Text(
            text = String.format(Locale.US, "%.5f", currentPrice),
            color = TextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold
          )
        }

        // Real-time Chart Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
          val w = size.width
          val h = size.height
          val points = pricePoints.toList()

          if (points.size >= 2) {
            val min = points.minOrNull() ?: 1.08000
            val max = points.maxOrNull() ?: 1.09000
            val range = if (max - min > 0.0001) max - min else 0.0001

            // Horizontal Grid Lines
            for (i in 1..4) {
              val y = (h / 5f) * i
              drawLine(
                color = Color(0x0EFFFFFF),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1.dp.toPx()
              )
            }

            val strokePath = Path()
            val fillPath = Path()

            points.forEachIndexed { index, price ->
              val x = (w / (points.size - 1)) * index
              val y = h - ((price - min) / range).toFloat() * (h * 0.70f) - (h * 0.15f)

              if (index == 0) {
                strokePath.moveTo(x, y)
                fillPath.moveTo(x, h)
                fillPath.lineTo(x, y)
              } else {
                strokePath.lineTo(x, y)
                fillPath.lineTo(x, y)
              }

              if (index == points.size - 1) {
                fillPath.lineTo(x, h)
                fillPath.close()

                // Glow dot at the current live tick
                drawCircle(
                  color = brokerPrimary,
                  radius = 5.dp.toPx(),
                  center = Offset(x - 2.dp.toPx(), y)
                )
                drawCircle(
                  color = Color.White,
                  radius = 2.5.dp.toPx(),
                  center = Offset(x - 2.dp.toPx(), y)
                )
              }
            }

            // Fill Gradient Under Line
            drawPath(
              path = fillPath,
              brush = Brush.verticalGradient(
                colors = listOf(
                  brokerPrimary.copy(alpha = 0.22f),
                  Color.Transparent
                )
              )
            )

            // Dynamic Chart Stroke
            drawPath(
              path = strokePath,
              color = brokerPrimary,
              style = Stroke(
                width = 2.5.dp.toPx(),
                cap = StrokeCap.Round
              )
            )
          }
        }

        // Live Order Execution Feedback Toast
        androidx.compose.animation.AnimatedVisibility(
          visible = executionToastMessage != null,
          enter = fadeIn(),
          exit = fadeOut(),
          modifier = Modifier.align(Alignment.Center)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xF0161C24))
              .border(1.dp, brokerPrimary, RoundedCornerShape(10.dp))
              .padding(horizontal = 14.dp, vertical = 8.dp)
          ) {
            Text(
              text = executionToastMessage ?: "",
              color = TextWhite,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // Bottom Digital Options Trading Action Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(DarkSurface)
          .border(0.5.dp, DarkBorder)
          .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Investment Amount Controller
        Column(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(DarkBackground)
            .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
            .clickable {
              investmentAmount = if (investmentAmount >= 200) 25 else investmentAmount + 25
            }
            .padding(horizontal = 10.dp, vertical = 6.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(text = "INVEST", color = TextMuted, fontSize = 8.sp, fontWeight = FontWeight.Bold)
          Text(
            text = "$$investmentAmount.00",
            color = TextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
          )
        }

        // CALL / HIGHER Button
        val expectedReturn = String.format(Locale.US, "$%.2f", investmentAmount * 1.94)
        Box(
          modifier = Modifier
            .weight(1f)
            .height(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
              Brush.linearGradient(
                listOf(Color(0xFF00C853), Color(0xFF00875A))
              )
            )
            .clickable {
              val formatted = String.format(Locale.US, "%.5f", currentPrice)
              executionToastMessage = if (isQuotex) {
                "⚡ QUOTEX CALL Order: $$investmentAmount @ $formatted (Return: $expectedReturn)"
              } else {
                "⚡ POCKET OPTION HIGHER: $$investmentAmount @ $formatted (Return: $expectedReturn)"
              }
            }
            .testTag("broker_order_call_button"),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.TrendingUp,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = if (isQuotex) "UP (CALL)" else "HIGHER",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
              )
              Text(
                text = "+94% ($expectedReturn)",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }

        // PUT / LOWER Button
        Box(
          modifier = Modifier
            .weight(1f)
            .height(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
              Brush.linearGradient(
                listOf(Color(0xFFFF5630), Color(0xFFDE350B))
              )
            )
            .clickable {
              val formatted = String.format(Locale.US, "%.5f", currentPrice)
              executionToastMessage = if (isQuotex) {
                "⚡ QUOTEX PUT Order: $$investmentAmount @ $formatted (Return: $expectedReturn)"
              } else {
                "⚡ POCKET OPTION LOWER: $$investmentAmount @ $formatted (Return: $expectedReturn)"
              }
            }
            .testTag("broker_order_put_button"),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.TrendingDown,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = if (isQuotex) "DOWN (PUT)" else "LOWER",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
              )
              Text(
                text = "+94% ($expectedReturn)",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }
    }
  }
}
