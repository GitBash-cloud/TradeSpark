package com.example.ui.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBg
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite

/**
 * Status toggle & badge on top of the dashboard for OTC Market Mode.
 * Specifically configured for Quotex and Pocket Option OTC 24/7 trading with SMC logic.
 */
@Composable
fun OtcMarketModeBar(
  isOtcModeEnabled: Boolean,
  onToggleOtcMode: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  val animatedBorderColor by animateColorAsState(
    targetValue = if (isOtcModeEnabled) ProfitGreenBright.copy(alpha = 0.5f) else DarkBorder,
    label = "otc_border_color"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(16.dp))
      .border(1.dp, animatedBorderColor, RoundedCornerShape(16.dp))
      .testTag("otc_market_mode_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.linearGradient(
            colors = if (isOtcModeEnabled) {
              listOf(
                Color(0xFF10261E), // Subtle dark emerald
                Color(0xFF161C24),
                Color(0xFF182230)
              )
            } else {
              listOf(
                Color(0xFF161C24),
                Color(0xFF161C24)
              )
            }
          )
        )
        .padding(14.dp)
    ) {
      // Top Row: Title, Status Badge, and Switch Toggle
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Status indicator dot (Glowing when active)
          Box(
            modifier = Modifier
              .size(10.dp)
              .clip(CircleShape)
              .background(if (isOtcModeEnabled) ProfitGreenBright else Color.Gray)
          )
          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "OTC MARKET MODE",
                color = TextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.8.sp
              )
              Spacer(modifier = Modifier.width(6.dp))

              // Status badge
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (isOtcModeEnabled) ProfitGreenBg else Color(0xFF232B36))
                  .border(
                    0.5.dp,
                    if (isOtcModeEnabled) ProfitGreenBright.copy(alpha = 0.4f) else DarkBorder,
                    RoundedCornerShape(4.dp)
                  )
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = if (isOtcModeEnabled) "ACTIVE 24/7" else "OFF",
                  color = if (isOtcModeEnabled) ProfitGreenBright else TextMuted,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
              text = if (isOtcModeEnabled) {
                "Quotex & Pocket Option • 92%-98% Payouts"
              } else {
                "Standard Exchange Hours Mode"
              },
              color = if (isOtcModeEnabled) AccentCyan else TextMuted,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        // Toggle Switch
        Switch(
          checked = isOtcModeEnabled,
          onCheckedChange = onToggleOtcMode,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = ProfitGreen,
            uncheckedThumbColor = TextMuted,
            uncheckedTrackColor = DarkBackground,
            uncheckedBorderColor = DarkBorder
          ),
          modifier = Modifier.testTag("otc_market_mode_toggle")
        )
      }

      if (isOtcModeEnabled) {
        Spacer(modifier = Modifier.height(10.dp))

        // Bottom quick info tags
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Tag 1: SMC Precision
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFF1C2735))
              .border(0.5.dp, Color(0xFF2C3E55), RoundedCornerShape(6.dp))
              .padding(horizontal = 7.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Psychology,
              contentDescription = null,
              tint = AccentCyan,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "SMC Algorithmic Precision",
              color = AccentCyan,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // Tag 2: 60s - 300s Expiry
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFF2B2213))
              .border(0.5.dp, AccentGold.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
              .padding(horizontal = 7.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Bolt,
              contentDescription = null,
              tint = AccentGold,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "1M / 5M Binary Expiry",
              color = AccentGold,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
