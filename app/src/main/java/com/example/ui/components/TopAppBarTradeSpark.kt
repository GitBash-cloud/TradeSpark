package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.LossRed
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun TopAppBarTradeSpark(
  unreadNotifications: Int = 3,
  onProfileClick: () -> Unit = {},
  onNotificationClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .statusBarsPadding()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    // Left: Profile Avatar with Status Indicator
    Box(
      modifier = Modifier
        .testTag("top_bar_profile_button")
        .clickable(onClick = onProfileClick)
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .border(1.5.dp, DarkBorder, CircleShape)
          .background(Color(0xFF1F2633)),
        contentAlignment = Alignment.Center
      ) {
        // Render generated avatar image
        Image(
          painter = painterResource(id = R.drawable.trader_avatar_1789548821647),
          contentDescription = "User Profile Avatar",
          modifier = Modifier.size(44.dp),
          contentScale = ContentScale.Crop
        )
      }

      // Online / Active AI Sync indicator dot
      Box(
        modifier = Modifier
          .size(11.dp)
          .clip(CircleShape)
          .background(ProfitGreenBright)
          .border(2.dp, Color(0xFF0B0E14), CircleShape)
          .align(Alignment.BottomEnd)
      )
    }

    // Center: Logo / Brand Name "TradeSpark"
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.testTag("app_brand_header")
    ) {
      // Futuristic AI Logo Emblem
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(
            Brush.linearGradient(
              colors = listOf(ProfitGreen, AccentCyan)
            )
          ),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = "TradeSpark Emblem",
          tint = Color.White,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Trade",
            color = TextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Spark",
            color = ProfitGreenBright,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.5.sp
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(5.dp)
              .clip(CircleShape)
              .background(ProfitGreenBright)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "OTC • SMC Engine Active",
            color = ProfitGreenBright,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }

    // Right: Notification Bell with Badge
    IconButton(
      onClick = onNotificationClick,
      modifier = Modifier
        .testTag("notification_bell_button")
        .size(44.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(Color(0xFF161C24))
        .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
    ) {
      BadgedBox(
        badge = {
          if (unreadNotifications > 0) {
            Badge(
              containerColor = LossRed,
              contentColor = TextWhite
            ) {
              Text(
                text = unreadNotifications.toString(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      ) {
        Icon(
          imageVector = Icons.Default.Notifications,
          contentDescription = "Notifications",
          tint = TextWhite,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

