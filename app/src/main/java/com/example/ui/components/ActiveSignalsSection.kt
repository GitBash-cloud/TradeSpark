package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TradingSignal
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.LossRedBg
import com.example.ui.theme.LossRedBright
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBg
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite

@Composable
fun ActiveSignalsSection(
  signals: List<TradingSignal>,
  selectedCategory: String,
  onCategorySelected: (String) -> Unit,
  onCopySignal: (TradingSignal) -> Unit,
  modifier: Modifier = Modifier
) {
  val categories = listOf("All", "OTC", "Crypto", "Forex", "Indices", "Stocks")

  Column(modifier = modifier.fillMaxWidth()) {
    // Header Row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.FlashOn,
          contentDescription = null,
          tint = AccentGold,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "ACTIVE SIGNALS",
          color = TextWhite,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.2.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(ProfitGreenBg)
            .border(1.dp, ProfitGreenBright.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(
            text = "${signals.size} Live",
            color = ProfitGreenBright,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Text(
        text = "Auto-Synced",
        color = TextMuted,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
      )
    }

    // Category Filter Chips (All, OTC, Crypto, Forex, Indices, Stocks)
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)
        .testTag("signal_category_filter_row")
    ) {
      items(categories) { category ->
        val isSelected = selectedCategory.equals(category, ignoreCase = true)
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) ProfitGreen else DarkSurfaceElevated)
            .border(
              width = 1.dp,
              color = if (isSelected) ProfitGreenBright else DarkBorder,
              shape = RoundedCornerShape(8.dp)
            )
            .clickable { onCategorySelected(category) }
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("filter_chip_$category")
        ) {
          Text(
            text = category,
            color = if (isSelected) Color.White else TextMuted,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // List of Signal Cards
    val filteredSignals = when {
      selectedCategory.equals("All", ignoreCase = true) -> signals
      selectedCategory.equals("OTC", ignoreCase = true) -> signals.filter { it.isOtc || it.category.equals("OTC", ignoreCase = true) }
      else -> signals.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      if (filteredSignals.isEmpty()) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurface)
            .padding(24.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "No signals found in '$selectedCategory'. Check other categories.",
            color = TextMuted,
            fontSize = 13.sp
          )
        }
      } else {
        filteredSignals.forEach { signal ->
          SignalCard(
            signal = signal,
            onCopyClick = { onCopySignal(signal) }
          )
        }
      }
    }
  }
}

@Composable
fun SignalCard(
  signal: TradingSignal,
  onCopyClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var isExpanded by remember { mutableStateOf(false) }

  val isBullish = signal.type.isBullish
  val actionColor = if (isBullish) ProfitGreenBright else LossRedBright
  val actionBg = if (isBullish) ProfitGreenBg else LossRedBg
  val trendIcon = if (isBullish) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown

  // For OTC, display CALL (UP) / PUT (DOWN); for traditional, display BUY / LONG or SELL / SHORT
  val actionText = if (signal.isOtc) signal.type.binaryLabel else signal.type.label

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
      .testTag("signal_card_${signal.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // 1. Header: Pair, Timeframe, Category Badge & Confidence Accuracy Pill
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = signal.pair,
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp
          )
          Spacer(modifier = Modifier.width(8.dp))

          // Timeframe badge
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(DarkBackground)
              .border(1.dp, DarkBorder, RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = signal.timeframe,
              color = TextMuted,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          // OTC Badge vs Traditional Asset Category Badge
          if (signal.isOtc) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF2A1C3D))
                .border(0.8.dp, Color(0xFF9C27B0).copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
              Text(
                text = "OTC",
                color = Color(0xFFCE93D8),
                fontSize = 9.sp,
                fontWeight = FontWeight.ExtraBold
              )
            }
          } else {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(DarkBackground)
                .border(0.8.dp, DarkBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
              Text(
                text = signal.category.uppercase(),
                color = AccentCyan,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Confidence Score Pill (e.g., 94% Accuracy)
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x2200B8D9))
            .border(1.dp, AccentCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = AccentCyan,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "${signal.confidencePercentage}% Accuracy",
              color = AccentCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Trade Direction & Rationale/Risk-Reward Badge Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Trade Direction: CALL/PUT for OTC, BUY/SELL for Traditional
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(actionBg)
            .border(1.dp, actionColor.copy(alpha = 0.45f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = trendIcon,
            contentDescription = actionText,
            tint = actionColor,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = actionText,
            color = actionColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp
          )
        }

        // Rationale / Risk-Reward Badge
        if (signal.isOtc) {
          // SMC Rationale Badge for OTC (Quotex & Pocket Option)
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFF1E2838))
              .border(1.dp, Color(0xFF2C3E55), RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 5.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                tint = AccentCyan,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = signal.smcRationale,
                color = AccentCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        } else {
          // Traditional Risk/Reward Badge for Forex/Crypto/Indices/Stocks
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(DarkBackground)
              .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 5.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "R:R ",
                color = TextSubtle,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = signal.riskRewardRatio.ifEmpty { "1:2.5" },
                color = AccentGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 3. Parameters Grid:
      // OTC Mode: Strike / Entry, Expiry Time (1 Min / 5 Min), and Estimated Payout
      // Traditional Mode: Entry Price, Stop Loss (SL), and Take Profit (TP)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(DarkBackground)
          .border(1.dp, DarkBorder.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
          .padding(vertical = 10.dp, horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (signal.isOtc) {
          // --- OTC PARAMETERS ---
          // 1. Entry Strike
          Column(horizontalAlignment = Alignment.Start) {
            Text(
              text = "STRIKE / ENTRY",
              color = TextSubtle,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = signal.entryPrice,
              color = TextWhite,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Vertical Divider
          Box(
            modifier = Modifier
              .width(1.dp)
              .height(26.dp)
              .background(DarkBorder)
          )

          // 2. Expiry Time Duration (1 Min / 5 Min)
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                tint = AccentGold,
                modifier = Modifier.size(11.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "EXPIRY TIME",
                color = AccentGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = signal.expiryTime,
              color = TextWhite,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Vertical Divider
          Box(
            modifier = Modifier
              .width(1.dp)
              .height(26.dp)
              .background(DarkBorder)
          )

          // 3. Est. Payout
          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "EST. PAYOUT",
              color = TextSubtle,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = "${signal.payoutPercentage} Return",
              color = ProfitGreenBright,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        } else {
          // --- TRADITIONAL SL / TP PARAMETERS ---
          // 1. Entry Price
          Column(horizontalAlignment = Alignment.Start) {
            Text(
              text = "ENTRY",
              color = TextSubtle,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = signal.entryPrice,
              color = TextWhite,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Vertical Divider
          Box(
            modifier = Modifier
              .width(1.dp)
              .height(26.dp)
              .background(DarkBorder)
          )

          // 2. Stop Loss (SL)
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = LossRedBright,
                modifier = Modifier.size(11.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "STOP LOSS",
                color = LossRedBright,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = signal.stopLoss.ifEmpty { "Dynamic" },
              color = LossRedBright,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Vertical Divider
          Box(
            modifier = Modifier
              .width(1.dp)
              .height(26.dp)
              .background(DarkBorder)
          )

          // 3. Take Profit (TP)
          Column(horizontalAlignment = Alignment.End) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.TrackChanges,
                contentDescription = null,
                tint = ProfitGreenBright,
                modifier = Modifier.size(11.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "TAKE PROFIT",
                color = ProfitGreenBright,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
              )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = signal.takeProfit.ifEmpty { "Dynamic" },
              color = ProfitGreenBright,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // 4. Expandable Analysis / Thesis Section
      AnimatedVisibility(
        visible = isExpanded,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
      ) {
        Column(modifier = Modifier.padding(top = 12.dp)) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFF19222E))
              .border(1.dp, Color(0xFF263548), RoundedCornerShape(10.dp))
              .padding(12.dp)
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Psychology,
                  contentDescription = null,
                  tint = AccentCyan,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (signal.isOtc) "Smart Money Concept (SMC) OTC Thesis" else "Algorithmic Analysis & Strategy",
                  color = AccentCyan,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = signal.aiRationale,
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 17.sp
              )
              Spacer(modifier = Modifier.height(6.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = if (signal.isOtc) {
                    "Execution: Quotex / Pocket Option 60s - 300s Turbo Mode"
                  } else {
                    "Execution: Standard Market Order with SL & TP Protection"
                  },
                  color = TextSubtle,
                  fontSize = 10.sp
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 5. Footer Actions: Toggle Analysis & Copy Signal
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { isExpanded = !isExpanded }
            .padding(vertical = 4.dp, horizontal = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isExpanded) "Hide Analysis" else "View Analysis",
            color = AccentCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
          )
          Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null,
            tint = AccentCyan,
            modifier = Modifier.size(16.dp)
          )
        }

        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(DarkSurfaceElevated)
            .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
            .clickable(onClick = onCopyClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag("copy_signal_${signal.id}"),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = "Copy Setup",
            tint = TextWhite,
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(5.dp))
          Text(
            text = "Copy Setup",
            color = TextWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
