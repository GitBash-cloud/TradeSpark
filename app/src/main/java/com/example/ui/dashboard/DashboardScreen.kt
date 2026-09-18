package com.example.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import kotlinx.coroutines.delay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TradingMockData
import com.example.model.BrokerType
import com.example.model.DashboardTab
import com.example.model.MarketAsset
import com.example.model.TradingSignal
import com.example.ui.components.ActiveSignalsSection
import com.example.ui.components.BrokerSelectorToggle
import com.example.ui.components.BrokerWebViewCard
import com.example.ui.components.BrokerWebViewFullScreenDialog
import com.example.ui.components.ChartScannerDialog
import com.example.ui.components.MarketTickerTape
import com.example.ui.components.OtcMarketModeBar
import com.example.ui.components.QuickActionBanner
import com.example.ui.components.TopAppBarTradeSpark
import com.example.ui.components.TradeSparkBottomNavBar
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.ProfitGreen
import com.example.ui.theme.ProfitGreenBg
import com.example.ui.theme.ProfitGreenBright
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
  modifier: Modifier = Modifier
) {
  var currentTab by remember { mutableStateOf(DashboardTab.DASHBOARD) }
  var assetsList by remember { mutableStateOf(TradingMockData.majorAssets) }
  var signalsList by remember { mutableStateOf(TradingMockData.activeSignals) }
  var selectedAsset by remember { mutableStateOf<MarketAsset?>(assetsList.firstOrNull()) }
  var selectedCategory by remember { mutableStateOf("All") }
  var showScannerDialog by remember { mutableStateOf(false) }
  var notificationsCount by remember { mutableStateOf(3) }
  var isRefreshing by remember { mutableStateOf(false) }
  var isOtcModeEnabled by remember { mutableStateOf(true) }

  // Integrated Broker & WebView State
  var activeBroker by remember { mutableStateOf(BrokerType.QUOTEX) }
  var isWebViewVisible by remember { mutableStateOf(false) }
  var isWebViewFullScreen by remember { mutableStateOf(false) }

  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  val onRefreshData: () -> Unit = {
    scope.launch {
      isRefreshing = true
      delay(900)
      assetsList = TradingMockData.getRefreshedAssets(assetsList)
      signalsList = TradingMockData.getRefreshedSignals(signalsList)
      selectedAsset = assetsList.find { it.id == selectedAsset?.id } ?: assetsList.firstOrNull()
      isRefreshing = false
      snackbarHostState.showSnackbar("Market quotes and trading signals refreshed")
    }
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground),
    containerColor = DarkBackground,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      TradeSparkBottomNavBar(
        currentTab = currentTab,
        onTabSelected = { currentTab = it }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
      when (currentTab) {
        DashboardTab.DASHBOARD -> {
          DashboardContent(
            assets = assetsList,
            signals = signalsList,
            selectedAssetId = selectedAsset?.id,
            selectedCategory = selectedCategory,
            unreadNotifications = notificationsCount,
            activeBroker = activeBroker,
            isWebViewVisible = isWebViewVisible,
            isRefreshing = isRefreshing,
            isOtcModeEnabled = isOtcModeEnabled,
            onToggleOtcMode = { enabled ->
              isOtcModeEnabled = enabled
              scope.launch {
                snackbarHostState.showSnackbar(
                  if (enabled) "OTC Market Mode Activated (24/7 Quotex & Pocket Option Feeds)"
                  else "Standard Exchange Mode Active"
                )
              }
            },
            onRefresh = onRefreshData,
            onBrokerChanged = { broker ->
              activeBroker = broker
              scope.launch {
                snackbarHostState.showSnackbar("Active Broker switched to ${broker.displayName} (${broker.tagline})")
              }
            },
            onToggleWebView = { isWebViewVisible = !isWebViewVisible },
            onLaunchFullScreen = { isWebViewFullScreen = true },
            onAssetClick = { asset ->
              selectedAsset = asset
              scope.launch {
                val info = if (asset.isOtc) "Payout: ${asset.otcPayout}" else "24h Vol: ${asset.volume24h}"
                snackbarHostState.showSnackbar("Focused on ${asset.symbol}: ${asset.price} ($info)")
              }
            },
            onScanBannerClick = { showScannerDialog = true },
            onCategorySelected = { selectedCategory = it },
            onCopySignal = { signal ->
              val copyMsg = if (signal.isOtc) {
                "Copied ${signal.pair} ${signal.type.binaryLabel} (${signal.expiryTime} @ ${signal.entryPrice}) - ${signal.smcRationale}"
              } else {
                "Copied ${signal.pair} ${signal.type.label}: Entry ${signal.entryPrice} | SL ${signal.stopLoss} | TP ${signal.takeProfit}"
              }
              scope.launch {
                snackbarHostState.showSnackbar(copyMsg)
              }
            },
            onNotificationClick = {
              notificationsCount = 0
              scope.launch {
                snackbarHostState.showSnackbar("All notifications marked as read")
              }
            },
            onProfileClick = { currentTab = DashboardTab.PROFILE }
          )
        }

        DashboardTab.SCANNER -> {
          ScannerTabContent(onLaunchScanner = { showScannerDialog = true })
        }

        DashboardTab.SIGNALS -> {
          SignalsTabContent(
            signals = signalsList,
            selectedCategory = selectedCategory,
            isRefreshing = isRefreshing,
            onRefresh = onRefreshData,
            onCategorySelected = { selectedCategory = it },
            onCopySignal = { signal ->
              val copyMsg = if (signal.isOtc) {
                "Copied ${signal.pair} ${signal.type.binaryLabel} (${signal.expiryTime}) to clipboard"
              } else {
                "Copied ${signal.pair} ${signal.type.label}: Entry ${signal.entryPrice} | SL ${signal.stopLoss} | TP ${signal.takeProfit}"
              }
              scope.launch {
                snackbarHostState.showSnackbar(copyMsg)
              }
            }
          )
        }

        DashboardTab.ANALYTICS -> {
          AnalyticsTabContent()
        }

        DashboardTab.PROFILE -> {
          ProfileTabContent()
        }
      }
    }

    // Modal Chart Scanner Dialog
    if (showScannerDialog) {
      ChartScannerDialog(
        onDismiss = { showScannerDialog = false },
        onApplySignal = { signal ->
          signalsList = listOf(signal) + signalsList
          scope.launch {
            val label = if (signal.isOtc) "${signal.pair} ${signal.type.binaryLabel}" else "${signal.pair} ${signal.type.label}"
            snackbarHostState.showSnackbar("Deployed $label Signal to Active Feed!")
          }
        }
      )
    }

    // Modal Full Screen Broker WebView
    if (isWebViewFullScreen) {
      BrokerWebViewFullScreenDialog(
        broker = activeBroker,
        onDismiss = { isWebViewFullScreen = false }
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
  assets: List<MarketAsset>,
  signals: List<TradingSignal>,
  selectedAssetId: String?,
  selectedCategory: String,
  unreadNotifications: Int,
  activeBroker: BrokerType,
  isWebViewVisible: Boolean,
  isRefreshing: Boolean,
  isOtcModeEnabled: Boolean = true,
  onToggleOtcMode: (Boolean) -> Unit = {},
  onRefresh: () -> Unit,
  onBrokerChanged: (BrokerType) -> Unit,
  onToggleWebView: () -> Unit,
  onLaunchFullScreen: () -> Unit,
  onAssetClick: (MarketAsset) -> Unit,
  onScanBannerClick: () -> Unit,
  onCategorySelected: (String) -> Unit,
  onCopySignal: (TradingSignal) -> Unit,
  onNotificationClick: () -> Unit,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val pullRefreshState = rememberPullToRefreshState()

  PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = onRefresh,
    state = pullRefreshState,
    modifier = modifier
      .fillMaxSize()
      .testTag("dashboard_pull_to_refresh_box"),
    indicator = {
      PullToRefreshDefaults.Indicator(
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        modifier = Modifier.align(Alignment.TopCenter),
        containerColor = DarkSurfaceElevated,
        color = ProfitGreenBright
      )
    }
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(bottom = 24.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 1. Top App Bar
      item {
        TopAppBarTradeSpark(
          unreadNotifications = unreadNotifications,
          onProfileClick = onProfileClick,
          onNotificationClick = onNotificationClick
        )
      }

      // 2. OTC Market Mode Status Bar & Toggle
      item {
        OtcMarketModeBar(
          isOtcModeEnabled = isOtcModeEnabled,
          onToggleOtcMode = onToggleOtcMode
        )
      }

      // 3. Broker Selector Toggle (Quotex vs Pocket Option)
      item {
        BrokerSelectorToggle(
          activeBroker = activeBroker,
          isWebViewVisible = isWebViewVisible,
          onBrokerChanged = onBrokerChanged,
          onToggleWebView = onToggleWebView,
          onLaunchFullScreen = onLaunchFullScreen
        )
      }

      // 3. In-App Broker Web View Card Container (Collapsible)
      if (isWebViewVisible) {
        item {
          BrokerWebViewCard(
            broker = activeBroker,
            onClose = onToggleWebView,
            onFullScreen = onLaunchFullScreen
          )
        }
      }

      // 4. Market Overview / Ticker Tape
      item {
        MarketTickerTape(
          assets = assets,
          selectedAssetId = selectedAssetId,
          onAssetClick = onAssetClick,
          isRefreshing = isRefreshing,
          onRefreshClick = onRefresh
        )
      }

      // 5. Quick Action Banner
      item {
        QuickActionBanner(onScanClick = onScanBannerClick)
      }

      // 6. AI Performance Quick Stats Badge
      item {
        AiPerformanceStatCard()
      }

      // 7. Active AI Signals Section
      item {
        ActiveSignalsSection(
          signals = signals,
          selectedCategory = selectedCategory,
          onCategorySelected = onCategorySelected,
          onCopySignal = onCopySignal
        )
      }
    }
  }
}

@Composable
fun AiPerformanceStatCard() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clip(RoundedCornerShape(16.dp))
      .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(ProfitGreenBg)
            .border(1.dp, ProfitGreenBright.copy(alpha = 0.5f), CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = ProfitGreenBright,
            modifier = Modifier.size(18.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "AI Accuracy (Last 30 Days)",
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "89.4% Win Rate (43/48)",
            color = TextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(ProfitGreenBg)
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
            contentDescription = null,
            tint = ProfitGreenBright,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "+38.4% ROI",
            color = ProfitGreenBright,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}

@Composable
fun ScannerTabContent(onLaunchScanner: () -> Unit) {
  var selectedMode by remember { mutableStateOf("Standard Forex") }
  val modes = listOf("Standard Forex", "OTC Binary (Quotex/PO)")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    // 1. SUBTLE MODE TOGGLE at the top: ["Standard Forex" | "OTC Binary (Quotex/PO)"]
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(DarkBackground)
        .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
        .padding(3.dp)
        .testTag("scanner_screen_mode_toggle"),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      modes.forEach { mode ->
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
            .clickable { selectedMode = mode }
            .padding(vertical = 8.dp)
            .testTag("tab_scanner_mode_$mode"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = mode,
            color = if (isSelected) Color.White else TextMuted,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    Box(
      modifier = Modifier
        .size(80.dp)
        .clip(CircleShape)
        .background(
          Brush.linearGradient(
            colors = listOf(ProfitGreen, AccentCyan)
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.CameraAlt,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(38.dp)
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Original Header Text
    Text(
      text = "Scan Chart & Get AI Insights",
      color = TextWhite,
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Original Subtext
    Text(
      text = "Instant candlestick detection, support/resistance levels & automated SL/TP setup.",
      color = TextMuted,
      fontSize = 13.sp,
      textAlign = androidx.compose.ui.text.style.TextAlign.Center,
      lineHeight = 18.sp
    )

    Spacer(modifier = Modifier.height(28.dp))

    // Original Primary Button: "Scan Chart Now" alongside the upload icon button
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Button(
        onClick = onLaunchScanner,
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("scanner_tab_scan_chart_button"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = ProfitGreen,
          contentColor = Color.White
        )
      ) {
        Icon(
          imageVector = Icons.Default.CameraAlt,
          contentDescription = null,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Scan Chart Now",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFF222B38))
          .border(1.dp, Color(0xFF2E3B4D), RoundedCornerShape(12.dp))
          .clickable(onClick = onLaunchScanner)
          .testTag("scanner_tab_upload_icon_button"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.FileUpload,
          contentDescription = "Upload Chart Screenshot",
          tint = TextWhite,
          modifier = Modifier.size(22.dp)
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignalsTabContent(
  signals: List<TradingSignal>,
  selectedCategory: String,
  isRefreshing: Boolean = false,
  onRefresh: (() -> Unit)? = null,
  onCategorySelected: (String) -> Unit,
  onCopySignal: (TradingSignal) -> Unit
) {
  val pullRefreshState = rememberPullToRefreshState()

  PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = { onRefresh?.invoke() },
    state = pullRefreshState,
    modifier = Modifier
      .fillMaxSize()
      .testTag("signals_pull_to_refresh_box"),
    indicator = {
      PullToRefreshDefaults.Indicator(
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        modifier = Modifier.align(Alignment.TopCenter),
        containerColor = DarkSurfaceElevated,
        color = ProfitGreenBright
      )
    }
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "AI Trading Signals",
              color = TextWhite,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Real-time algorithmic trade setups (Pull to refresh)",
              color = TextMuted,
              fontSize = 12.sp
            )
          }
        }
      }

      item {
        ActiveSignalsSection(
          signals = signals,
          selectedCategory = selectedCategory,
          onCategorySelected = onCategorySelected,
          onCopySignal = onCopySignal
        )
      }
    }
  }
}

@Composable
fun AnalyticsTabContent() {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = "Performance & Analytics",
        color = TextWhite,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Verified algorithmic signal performance",
        color = TextMuted,
        fontSize = 12.sp
      )
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(text = "Overall OTC Win Rate", color = TextMuted, fontSize = 12.sp)
          Text(text = "92.4%", color = ProfitGreenBright, fontSize = 28.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.height(12.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(text = "OTC Trades", color = TextMuted, fontSize = 11.sp)
              Text(text = "142 Live", color = TextWhite, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Column {
              Text(text = "Avg Return", color = TextMuted, fontSize = 11.sp)
              Text(text = "94.2%", color = AccentCyan, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Column {
              Text(text = "Net Profit", color = TextMuted, fontSize = 11.sp)
              Text(text = "+$18,450", color = ProfitGreenBright, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
fun ProfileTabContent() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Trader Profile",
      color = TextWhite,
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(16.dp))

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .border(1.dp, DarkBorder, RoundedCornerShape(16.dp)),
      colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(50.dp)
              .clip(CircleShape)
              .background(ProfitGreenBg),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Security,
              contentDescription = null,
              tint = ProfitGreenBright
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(text = "Alex Mercer", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "AI Elite Plan • Active", color = ProfitGreenBright, fontSize = 12.sp)
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Auto SL/TP Sync", color = TextMuted, fontSize = 13.sp)
          Text(text = "Enabled", color = ProfitGreenBright, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Broker Connection", color = TextMuted, fontSize = 13.sp)
          Text(text = "Binance & MT5 Connected", color = TextWhite, fontSize = 13.sp)
        }
      }
    }
  }
}
