package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.model.BrokerType
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun BrokerSelectorToggle(
  activeBroker: BrokerType,
  isWebViewVisible: Boolean,
  onBrokerChanged: (BrokerType) -> Unit,
  onToggleWebView: () -> Unit,
  onLaunchFullScreen: () -> Unit,
  modifier: Modifier = Modifier
) {
  val activeColor = Color(activeBroker.primaryColorHex)
  val accentColor = Color(activeBroker.accentColorHex)

  Card(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(16.dp))
      .border(
        width = 1.dp,
        brush = Brush.linearGradient(
          colors = listOf(
            activeColor.copy(alpha = 0.6f),
            DarkBorder
          )
        ),
        shape = RoundedCornerShape(16.dp)
      )
      .testTag("broker_selector_container"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      // Top Label & Server Health
      Row(
        modifier = Modifier.fillMaxWidth(),
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
            text = "INTEGRATED BROKER TERMINAL",
            color = TextWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp
          )
        }

        // Latency Badge
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(DarkBackground)
            .border(1.dp, DarkBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Speed,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(11.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            text = activeBroker.latency,
            color = accentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Dual Broker Switcher Bar (Quotex vs Pocket Option)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(DarkBackground)
          .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
          .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        BrokerOptionItem(
          broker = BrokerType.QUOTEX,
          isSelected = activeBroker == BrokerType.QUOTEX,
          onClick = { onBrokerChanged(BrokerType.QUOTEX) },
          modifier = Modifier.weight(1f)
        )

        BrokerOptionItem(
          broker = BrokerType.POCKET_OPTION,
          isSelected = activeBroker == BrokerType.POCKET_OPTION,
          onClick = { onBrokerChanged(BrokerType.POCKET_OPTION) },
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Broker Status & Web View Toggle Action Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = activeBroker.tagline,
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          // Toggle In-App WebView Card
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(if (isWebViewVisible) activeColor.copy(alpha = 0.2f) else DarkSurfaceElevated)
              .border(
                1.dp,
                if (isWebViewVisible) activeColor else DarkBorder,
                RoundedCornerShape(8.dp)
              )
              .clickable(onClick = onToggleWebView)
              .padding(horizontal = 8.dp, vertical = 5.dp)
              .testTag("toggle_broker_webview_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (isWebViewVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = null,
                tint = if (isWebViewVisible) activeColor else TextWhite,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (isWebViewVisible) "Hide Terminal" else "Show Terminal",
                color = if (isWebViewVisible) activeColor else TextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          // Full-Screen Web View Button
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(DarkSurfaceElevated)
              .border(1.dp, DarkBorder, RoundedCornerShape(8.dp))
              .clickable(onClick = onLaunchFullScreen)
              .padding(horizontal = 8.dp, vertical = 5.dp)
              .testTag("fullscreen_broker_webview_button"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.OpenInBrowser,
                contentDescription = "Full Screen",
                tint = activeColor,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Full Screen",
                color = activeColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun BrokerOptionItem(
  broker: BrokerType,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val brokerPrimary = Color(broker.primaryColorHex)
  val brokerAccent = Color(broker.accentColorHex)

  val bgColor = if (isSelected) {
    if (broker == BrokerType.QUOTEX) Color(0xFF0F263B) else Color(0xFF132047)
  } else {
    Color.Transparent
  }

  val borderColor = if (isSelected) brokerPrimary else Color.Transparent

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(bgColor)
      .border(1.dp, borderColor, RoundedCornerShape(10.dp))
      .clickable(onClick = onClick)
      .padding(vertical = 8.dp, horizontal = 10.dp)
      .testTag("broker_button_${broker.name.lowercase()}"),
    contentAlignment = Alignment.Center
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      // Broker Monogram Badge
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(
            if (isSelected) {
              Brush.linearGradient(listOf(brokerPrimary, brokerAccent))
            } else {
              Brush.linearGradient(listOf(Color(0xFF283243), Color(0xFF1E2633)))
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = broker.shortName,
          color = Color.White,
          fontSize = 9.sp,
          fontWeight = FontWeight.ExtraBold
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      Column {
        Text(
          text = broker.displayName,
          color = if (isSelected) TextWhite else TextMuted,
          fontSize = 13.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
      }

      if (isSelected) {
        Spacer(modifier = Modifier.width(6.dp))
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          tint = brokerAccent,
          modifier = Modifier.size(14.dp)
        )
      }
    }
  }
}
