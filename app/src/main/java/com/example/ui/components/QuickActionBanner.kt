package com.example.ui.components

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun QuickActionBanner(
  onScanClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(16.dp))
      .border(
        width = 1.dp,
        brush = Brush.linearGradient(
          colors = listOf(
            ProfitGreenBright.copy(alpha = 0.5f),
            AccentCyan.copy(alpha = 0.3f),
            Color(0xFF283243)
          )
        ),
        shape = RoundedCornerShape(16.dp)
      )
      .clickable(onClick = onScanClick)
      .testTag("quick_action_scanner_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.linearGradient(
            colors = listOf(
              Color(0xFF0F3227), // Deep emerald glow
              Color(0xFF132838), // Dark cyan slate
              Color(0xFF161C24)  // Card dark surface
            )
          )
        )
        .padding(18.dp)
    ) {
      Column {
        // AI Badge tag
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x3300C853))
            .border(1.dp, ProfitGreenBright.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = ProfitGreenBright,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "AI VISION SCANNER",
            color = ProfitGreenBright,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Scan Chart & Get AI Insights",
              color = TextWhite,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Instant candlestick detection, support/resistance levels & automated SL/TP setup.",
              color = TextMuted,
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          // Glowing Camera/Upload Circular Badge
          Box(
            modifier = Modifier
              .size(52.dp)
              .clip(CircleShape)
              .background(
                Brush.radialGradient(
                  colors = listOf(
                    ProfitGreenBright.copy(alpha = 0.35f),
                    Color(0xFF16232E)
                  )
                )
              )
              .border(1.5.dp, ProfitGreenBright.copy(alpha = 0.7f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.CameraAlt,
              contentDescription = "Scan Chart",
              tint = ProfitGreenBright,
              modifier = Modifier.size(26.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // CTA Button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Button(
            onClick = onScanClick,
            modifier = Modifier
              .weight(1f)
              .height(42.dp)
              .testTag("scan_chart_cta_button"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ProfitGreen,
              contentColor = Color.White
            )
          ) {
            Icon(
              imageVector = Icons.Default.CameraAlt,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Scan Chart Now",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFF222B38))
              .border(1.dp, Color(0xFF2E3B4D), RoundedCornerShape(10.dp))
              .clickable(onClick = onScanClick)
              .testTag("upload_chart_cta_button"),
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
      }
    }
  }
}
