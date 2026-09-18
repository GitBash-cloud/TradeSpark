package com.example.model

enum class SignalType(val label: String, val binaryLabel: String, val isBullish: Boolean) {
  BUY("BUY / LONG", "CALL (UP)", true),
  SELL("SELL / SHORT", "PUT (DOWN)", false),
  CALL("CALL (UP)", "CALL (UP)", true),
  PUT("PUT (DOWN)", "PUT (DOWN)", false);

  val isCall: Boolean get() = isBullish
  val isBuy: Boolean get() = isBullish
}

data class MarketAsset(
  val id: String,
  val symbol: String,
  val name: String,
  val price: String,
  val changePercentage: String,
  val isBullish: Boolean,
  val sparklinePoints: List<Float>,
  val assetType: String = "Crypto",
  val volume24h: String = "$28.4B",
  val high24h: String = "",
  val low24h: String = "",
  val isOtc: Boolean = false,
  val otcPayout: String = "92%"
)

data class TradingSignal(
  val id: String,
  val pair: String,
  val type: SignalType,
  val timeframe: String = "1H",
  val entryPrice: String,
  val stopLoss: String = "",
  val takeProfit: String = "",
  val riskRewardRatio: String = "",
  val expiryTime: String = "1 Min Expiry",
  val confidencePercentage: Int = 92,
  val smcRationale: String = "Order Block Rejection",
  val timestamp: String = "Just now",
  val aiRationale: String,
  val payoutPercentage: String = "92%",
  val isOtc: Boolean = false,
  val category: String = "Crypto"
)

enum class DashboardTab(val title: String) {
  DASHBOARD("Dashboard"),
  SCANNER("AI Scanner"),
  SIGNALS("Signals"),
  ANALYTICS("Analytics"),
  PROFILE("Profile")
}

enum class BrokerType(
  val displayName: String,
  val shortName: String,
  val tagline: String,
  val defaultUrl: String,
  val latency: String,
  val primaryColorHex: Long,
  val accentColorHex: Long,
  val badgeText: String
) {
  QUOTEX(
    displayName = "Quotex",
    shortName = "QX",
    tagline = "Smart Binary & Digital Options",
    defaultUrl = "https://qxbroker.com",
    latency = "12ms",
    primaryColorHex = 0xFF00A3FF,
    accentColorHex = 0xFF00E676,
    badgeText = "QX PRO"
  ),
  POCKET_OPTION(
    displayName = "Pocket Option",
    shortName = "PO",
    tagline = "Quick Trading & High Yields",
    defaultUrl = "https://pocketoption.com",
    latency = "16ms",
    primaryColorHex = 0xFF2979FF,
    accentColorHex = 0xFFFFB800,
    badgeText = "PO LIVE"
  )
}

