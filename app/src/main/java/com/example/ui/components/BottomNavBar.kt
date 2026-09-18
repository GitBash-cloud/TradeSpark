package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DashboardTab
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun TradeSparkBottomNavBar(
  currentTab: DashboardTab,
  onTabSelected: (DashboardTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(DarkSurface)
      .border(
        width = 1.dp,
        color = DarkBorder,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
      )
      .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
      .navigationBarsPadding()
      .testTag("bottom_navigation_bar")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      DashboardTab.entries.forEach { tab ->
        val isSelected = currentTab == tab
        val icon = when (tab) {
          DashboardTab.DASHBOARD -> Icons.Default.Dashboard
          DashboardTab.SCANNER -> Icons.Default.CameraAlt
          DashboardTab.SIGNALS -> Icons.Default.FlashOn
          DashboardTab.ANALYTICS -> Icons.Default.Analytics
          DashboardTab.PROFILE -> Icons.Default.Person
        }

        BottomNavItem(
          title = tab.title,
          icon = icon,
          isSelected = isSelected,
          onClick = { onTabSelected(tab) },
          modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
        )
      }
    }
  }
}

@Composable
private fun BottomNavItem(
  title: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val iconColor by animateColorAsState(
    targetValue = if (isSelected) ProfitGreenBright else TextMuted,
    label = "iconColor"
  )
  val textColor by animateColorAsState(
    targetValue = if (isSelected) TextWhite else TextMuted,
    label = "textColor"
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = modifier
      .minimumInteractiveComponentSize()
      .clip(RoundedCornerShape(12.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
      )
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(if (isSelected) Color(0x2400875A) else Color.Transparent)
        .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = iconColor,
        modifier = Modifier.size(22.dp)
      )
    }

    Spacer(modifier = Modifier.height(2.dp))

    Text(
      text = title,
      color = textColor,
      fontSize = 11.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
    )

    if (isSelected) {
      Box(
        modifier = Modifier
          .padding(top = 2.dp)
          .size(4.dp)
          .clip(CircleShape)
          .background(ProfitGreenBright)
      )
    }
  }
}


