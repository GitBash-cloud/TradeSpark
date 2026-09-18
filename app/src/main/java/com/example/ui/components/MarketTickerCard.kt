package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ShowChart
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
import com.example.model.MarketAsset
import com.example.ui.theme.AccentCyan
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
fun MarketTickerTape(
  assets: List<MarketAsset>,
  selectedAssetId: String?,
  onAssetClick: (MarketAsset) -> Unit,
  isRefreshing: Boolean = false,
  onRefreshClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  var selectedAssetCategory by remember { mutableStateOf("All") }
  val assetCategories = listOf("All", "OTC", "Crypto", "Forex", "Indices", "Stocks")

  val filteredAssets = remember(selectedAssetCategory, assets) {
    if (selectedAssetCategory == "All") {
      assets
    } else if (selectedAssetCategory == "OTC") {
      assets.filter { it.isOtc }
    } else {
      assets.filter { it.assetType.equals(selectedAssetCategory, ignoreCase = true) }
    }
  }

  Column(modifier = modifier.fillMaxWidth()) {
    // Header Row with Title and Refresh Status
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.ShowChart,
          contentDescription = null,
          tint = ProfitGreenBright,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "MARKET OVERVIEW",
          color = TextWhite,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.2.sp
        )
        Spacer(modifier = Modifier.width(6.dp))
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF2B1D3A))
            .border(0.8.dp, Color(0xFFAB47BC).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
            .padding(horizontal = 5.dp, vertical = 2.dp)
        ) {
          Text(
            text = "LIVE",
            color = Color(0xFFCE93D8),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(DarkSurfaceElevated)
          .border(0.5.dp, DarkBorder, RoundedCornerShape(6.dp))
          .then(if (onRefreshClick != null) Modifier.clickable { onRefreshClick() } else Modifier)
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .testTag("market_refresh_indicator")
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (isRefreshing) {
            androidx.compose.material3.CircularProgressIndicator(
              modifier = Modifier.size(10.dp),
              color = ProfitGreenBright,
              strokeWidth = 1.5.dp
            )
          } else {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(ProfitGreenBright)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isRefreshing) "Syncing..." else "Sync",
            color = if (isRefreshing) ProfitGreenBright else TextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    // Asset Category Filter Chips (All, OTC, Crypto, Forex, Indices, Stocks)
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .testTag("ticker_category_filter_row")
    ) {
      items(assetCategories) { cat ->
        val isSelected = selectedAssetCategory == cat
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) ProfitGreen else DarkSurfaceElevated)
            .border(
              width = 1.dp,
              color = if (isSelected) ProfitGreenBright else DarkBorder,
              shape = RoundedCornerShape(6.dp)
            )
            .clickable { selectedAssetCategory = cat }
            .padding(horizontal = 9.dp, vertical = 4.dp)
            .testTag("ticker_filter_$cat")
        ) {
          Text(
            text = cat,
            color = if (isSelected) Color.White else TextMuted,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Horizontal Scrolling Asset Cards
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("market_overview_ticker_row")
    ) {
      items(filteredAssets, key = { it.id }) { asset ->
        MarketTickerCard(
          asset = asset,
          isSelected = asset.id == selectedAssetId,
          onClick = { onAssetClick(asset) }
        )
      }
    }
  }
}

@Composable
fun MarketTickerCard(
  asset: MarketAsset,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderColor = if (isSelected) ProfitGreenBright else DarkBorder
  val cardBg = if (isSelected) DarkSurfaceElevated else DarkSurface

  Card(
    modifier = modifier
      .width(172.dp)
      .clip(RoundedCornerShape(16.dp))
      .border(1.dp, borderColor, RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .testTag("ticker_card_${asset.symbol.replace("/", "_").replace(" ", "_")}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = cardBg),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .padding(14.dp)
        .fillMaxWidth()
    ) {
      // Asset Symbol and Type Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = asset.symbol,
              color = TextWhite,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              maxLines = 1
            )
          }
          Text(
            text = asset.name,
            color = TextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1
          )
        }

        // Bullish / Bearish Change Badge
        val badgeBg = if (asset.isBullish) ProfitGreenBg else LossRedBg
        val badgeColor = if (asset.isBullish) ProfitGreenBright else LossRedBright
        val trendIcon = if (asset.isBullish) {
          Icons.AutoMirrored.Filled.TrendingUp
        } else {
          Icons.AutoMirrored.Filled.TrendingDown
        }

        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(badgeBg)
            .padding(horizontal = 5.dp, vertical = 2.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = trendIcon,
            contentDescription = null,
            tint = badgeColor,
            modifier = Modifier.size(10.dp)
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = asset.changePercentage,
            color = badgeColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Current Price & Payout / Asset Type Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = asset.price,
          color = TextWhite,
          fontSize = 15.sp,
          fontWeight = FontWeight.ExtraBold,
          letterSpacing = 0.3.sp
        )

        if (asset.isOtc) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(ProfitGreenBg)
              .border(0.5.dp, ProfitGreenBright.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
              .padding(horizontal = 4.dp, vertical = 2.dp)
          ) {
            Text(
              text = asset.otcPayout,
              color = ProfitGreenBright,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }
        } else {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(DarkBackground)
              .border(0.5.dp, DarkBorder, RoundedCornerShape(4.dp))
              .padding(horizontal = 4.dp, vertical = 2.dp)
          ) {
            Text(
              text = asset.assetType.uppercase(),
              color = TextSubtle,
              fontSize = 8.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Sparkline Graph
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(DarkBackground.copy(alpha = 0.5f))
          .padding(horizontal = 4.dp, vertical = 2.dp)
      ) {
        MiniSparkline(
          points = asset.sparklinePoints,
          isBullish = asset.isBullish
        )
      }
    }
  }
}
