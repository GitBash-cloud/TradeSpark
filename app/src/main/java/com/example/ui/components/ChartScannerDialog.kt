package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.SignalType
import com.example.model.TradingSignal
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.LossRedBright
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBg
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class ScannerMode(val label: String) {
  FOREX("Standard Forex"),
  OTC_BINARY("OTC Binary (Quotex/PO)")
}

@Composable
fun ChartScannerDialog(
  onDismiss: () -> Unit,
  onApplySignal: (TradingSignal) -> Unit
) {
  var selectedMode by remember { mutableStateOf(ScannerMode.FOREX) }
  var selectedExpiry by remember { mutableStateOf("1 Min") }
  var isScanning by remember { mutableStateOf(false) }
  var scanCompleted by remember { mutableStateOf(true) }

  val coroutineScope = rememberCoroutineScope()
  val scrollState = rememberScrollState()

  val infiniteTransition = rememberInfiniteTransition(label = "scan_laser")
  val laserProgress by infiniteTransition.animateFloat(
    initialValue = 0.05f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1800, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "laser_y"
  )

  fun triggerScan() {
    isScanning = true
    scanCompleted = false
    coroutineScope.launch {
      delay(1200)
      isScanning = false
      scanCompleted = true
    }
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .clip(RoundedCornerShape(20.dp))
        .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
        .testTag("chart_scanner_dialog"),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(scrollState)
          .padding(20.dp)
      ) {
        // Top Header Row with Close Button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(ProfitGreenBg)
                .border(1.dp, ProfitGreenBright, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                tint = ProfitGreenBright,
                modifier = Modifier.size(18.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Scan Chart & Get AI Insights",
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Instant candlestick detection, support/resistance levels & automated SL/TP setup.",
                color = TextMuted,
                fontSize = 11.sp,
                maxLines = 1
              )
            }
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 1. SUBTLE MODE TOGGLE AT THE TOP: ["Standard Forex" | "OTC Binary (Quotex/PO)"]
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(DarkBackground)
            .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
            .padding(3.dp)
            .testTag("scanner_mode_toggle"),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          ScannerMode.values().forEach { mode ->
            val isSelected = selectedMode == mode
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) ProfitGreen else Color.Transparent)
                .border(
                  width = if (isSelected) 1.dp else 0.dp,
                  color = if (isSelected) ProfitGreenBright else Color.Transparent,
                  shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                  selectedMode = mode
                  triggerScan()
                }
                .padding(vertical = 8.dp)
                .testTag("scanner_mode_${mode.name}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = mode.label,
                color = if (isSelected) Color.White else TextMuted,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Viewfinder Area with Candlestick backdrop & animated laser
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(DarkBackground)
            .border(1.dp, Color(0xFF1E2838), RoundedCornerShape(14.dp))
        ) {
          // Candlestick graphic backdrop
          Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height

            // Grid lines
            for (i in 1..4) {
              val y = h * (i / 5f)
              drawLine(
                color = Color(0x14FFFFFF),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1.dp.toPx()
              )
            }

            // Simulated candlesticks
            val candleCount = 12
            val spacing = w / (candleCount + 1)
            val candleHeights = listOf(
              0.4f to 0.6f, 0.45f to 0.7f, 0.35f to 0.55f, 0.5f to 0.8f,
              0.48f to 0.65f, 0.6f to 0.85f, 0.58f to 0.78f, 0.7f to 0.9f,
              0.65f to 0.82f, 0.72f to 0.95f, 0.8f to 0.98f, 0.75f to 0.92f
            )

            candleHeights.forEachIndexed { i, (lowRatio, highRatio) ->
              val cx = (i + 1) * spacing
              val topY = h * (1f - highRatio)
              val bottomY = h * (1f - lowRatio)
              val isGreen = i % 3 != 1
              val cColor = if (isGreen) Color(0xFF00E676) else Color(0xFFFF1744)

              // Wick
              drawLine(
                color = cColor,
                start = Offset(cx, topY - 10.dp.toPx()),
                end = Offset(cx, bottomY + 10.dp.toPx()),
                strokeWidth = 1.5.dp.toPx()
              )
              // Body
              drawRoundRect(
                color = cColor,
                topLeft = Offset(cx - 5.dp.toPx(), topY),
                size = androidx.compose.ui.geometry.Size(10.dp.toPx(), bottomY - topY)
              )
            }

            // Scanning laser
            val laserY = h * laserProgress
            drawLine(
              brush = Brush.horizontalGradient(
                colors = listOf(
                  Color.Transparent,
                  AccentCyan,
                  ProfitGreenBright,
                  AccentCyan,
                  Color.Transparent
                )
              ),
              start = Offset(0f, laserY),
              end = Offset(w, laserY),
              strokeWidth = 3.dp.toPx()
            )
          }

          // Watermark tag
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(8.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xBB000000))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = if (selectedMode == ScannerMode.FOREX) "EUR/USD • 1H" else "EUR/USD (OTC) • M1",
              color = TextWhite,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Detection banner
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(8.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(ProfitGreenBg)
              .border(1.dp, ProfitGreenBright.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = ProfitGreenBright,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (selectedMode == ScannerMode.FOREX) {
                  "Bull Flag Breakout + S/R Retest Detected"
                } else {
                  "Order Block Rejection + FVG Fill Detected"
                },
                color = ProfitGreenBright,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 3. DUAL ANALYSIS ENGINE DISPLAY
        if (selectedMode == ScannerMode.FOREX) {
          // --- STANDARD FOREX ANALYSIS ENGINE ---
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFF19212C))
              .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.AutoAwesome,
                  contentDescription = null,
                  tint = AccentCyan,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Technical Forex Diagnosis",
                  color = TextWhite,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Text(
                text = "93.8% Win Rate",
                color = AccentCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Traditional Metrics Grid: Entry, SL, TP, R:R
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(DarkBackground)
                .border(1.dp, DarkBorder.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Direction
              Column(horizontalAlignment = Alignment.Start) {
                Text(text = "SIGNAL", color = TextSubtle, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "BUY / LONG", color = ProfitGreenBright, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
              }

              Box(modifier = Modifier.width(1.dp).height(22.dp).background(DarkBorder))

              // Entry Price
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "ENTRY", color = TextSubtle, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "1.08450", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }

              Box(modifier = Modifier.width(1.dp).height(22.dp).background(DarkBorder))

              // Stop Loss
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "STOP LOSS", color = LossRedBright, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "1.08120", color = LossRedBright, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }

              Box(modifier = Modifier.width(1.dp).height(22.dp).background(DarkBorder))

              // Take Profit
              Column(horizontalAlignment = Alignment.End) {
                Text(text = "TAKE PROFIT", color = ProfitGreenBright, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "1.09250", color = ProfitGreenBright, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Risk-to-Reward Ratio (R:R)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Risk-to-Reward Ratio: ", color = TextMuted, fontSize = 11.sp)
                Text(text = "1:2.52", color = AccentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
              Text(
                text = "Timeframe: 1H Chart",
                color = TextSubtle,
                fontSize = 11.sp
              )
            }
          }
        } else {
          // --- OTC BINARY (QUOTEX / POCKET OPTION) ANALYSIS ENGINE ---
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFF19212C))
              .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Psychology,
                  contentDescription = null,
                  tint = AccentCyan,
                  modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "SMC Binary Diagnosis (Quotex/PO)",
                  color = TextWhite,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Text(
                text = "95.4% Win Probability",
                color = AccentCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Expiry Time Selector: [1 Min | 3 Min | 5 Min]
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Contract Expiry:",
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )

              Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("1 Min", "3 Min", "5 Min").forEach { exp ->
                  val isExpSelected = selectedExpiry == exp
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(if (isExpSelected) ProfitGreen else DarkBackground)
                      .border(
                        1.dp,
                        if (isExpSelected) ProfitGreenBright else DarkBorder,
                        RoundedCornerShape(6.dp)
                      )
                      .clickable { selectedExpiry = exp }
                      .padding(horizontal = 8.dp, vertical = 3.dp)
                      .testTag("expiry_selector_$exp")
                  ) {
                    Text(
                      text = exp,
                      color = if (isExpSelected) Color.White else TextMuted,
                      fontSize = 10.sp,
                      fontWeight = if (isExpSelected) FontWeight.Bold else FontWeight.Medium
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Binary Parameters: Direction (CALL/PUT), Expiry Time, Strike Entry, Est. Payout
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(DarkBackground)
                .border(1.dp, DarkBorder.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Direction (CALL / PUT)
              Column(horizontalAlignment = Alignment.Start) {
                Text(text = "DIRECTION", color = TextSubtle, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "CALL (UP)", color = ProfitGreenBright, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
              }

              Box(modifier = Modifier.width(1.dp).height(22.dp).background(DarkBorder))

              // Expiry Time
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "EXPIRY", color = AccentGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "$selectedExpiry Expiry", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }

              Box(modifier = Modifier.width(1.dp).height(22.dp).background(DarkBorder))

              // Strike Entry
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "STRIKE ENTRY", color = TextSubtle, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "1.08640", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }

              Box(modifier = Modifier.width(1.dp).height(22.dp).background(DarkBorder))

              // Est. Payout
              Column(horizontalAlignment = Alignment.End) {
                Text(text = "EST. PAYOUT", color = TextSubtle, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "+94% Yield", color = ProfitGreenBright, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // SMC Rationale Badge
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "SMC Rationale: ", color = TextMuted, fontSize = 11.sp)
                Text(text = "Order Block Rejection", color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }
              Text(
                text = "Feed: Pocket Option / Quotex",
                color = TextSubtle,
                fontSize = 10.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4. ORIGINAL PRIMARY BUTTON: "Scan Chart Now" alongside upload icon button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Button(
            onClick = { triggerScan() },
            modifier = Modifier
              .weight(1f)
              .height(44.dp)
              .testTag("scan_chart_now_primary_button"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ProfitGreen,
              contentColor = Color.White
            )
          ) {
            if (isScanning) {
              CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = Color.White,
                strokeWidth = 2.dp
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("Scanning Candlesticks...", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            } else {
              Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("Scan Chart Now", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
          }

          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFF222B38))
              .border(1.dp, Color(0xFF2E3B4D), RoundedCornerShape(10.dp))
              .clickable { triggerScan() }
              .testTag("upload_chart_icon_button"),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.FileUpload,
              contentDescription = "Upload Chart Screenshot",
              tint = TextWhite,
              modifier = Modifier.size(20.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 5. Deploy / Apply Signal Button to Active Feed
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier
              .weight(1f)
              .height(42.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = TextWhite
            )
          ) {
            Text("Cancel", fontSize = 13.sp)
          }

          Button(
            onClick = {
              val generatedSignal = if (selectedMode == ScannerMode.FOREX) {
                TradingSignal(
                  id = "sig_forex_${System.currentTimeMillis()}",
                  pair = "EUR/USD",
                  type = SignalType.BUY,
                  timeframe = "1H",
                  entryPrice = "1.08450",
                  stopLoss = "1.08120",
                  takeProfit = "1.09250",
                  riskRewardRatio = "1:2.52",
                  confidencePercentage = 94,
                  smcRationale = "Bull Flag Breakout",
                  timestamp = "Just now",
                  aiRationale = "Technical AI diagnosis identified clean ascending channel breakout with 1.08450 demand zone confirmation.",
                  isOtc = false,
                  category = "Forex"
                )
              } else {
                TradingSignal(
                  id = "sig_otc_${System.currentTimeMillis()}",
                  pair = "EUR/USD (OTC)",
                  type = SignalType.CALL,
                  timeframe = "M1",
                  expiryTime = "$selectedExpiry Expiry",
                  entryPrice = "1.08640",
                  confidencePercentage = 95,
                  smcRationale = "Order Block Rejection",
                  timestamp = "Just now",
                  aiRationale = "Quotex/PO OTC feed retested bullish Order Block at key discount level with Fair Value Gap (FVG) mitigation.",
                  payoutPercentage = "94%",
                  isOtc = true,
                  category = "OTC"
                )
              }
              onApplySignal(generatedSignal)
              onDismiss()
            },
            modifier = Modifier
              .weight(1.8f)
              .height(42.dp)
              .testTag("deploy_scanned_signal_button"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (selectedMode == ScannerMode.FOREX) Color(0xFF1E3A5F) else ProfitGreen
            )
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (selectedMode == ScannerMode.FOREX) "Deploy Forex Signal" else "Deploy OTC Signal",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
