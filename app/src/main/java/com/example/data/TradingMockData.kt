package com.example.data

import com.example.model.MarketAsset
import com.example.model.SignalType
import com.example.model.TradingSignal

object TradingMockData {
  val majorAssets = listOf(
    // Crypto
    MarketAsset(
      id = "btc_usdt",
      symbol = "BTC/USDT",
      name = "Bitcoin",
      price = "$64,850.20",
      changePercentage = "+3.42%",
      isBullish = true,
      sparklinePoints = listOf(0.25f, 0.38f, 0.45f, 0.42f, 0.55f, 0.68f, 0.72f, 0.80f, 0.90f, 0.96f),
      assetType = "Crypto",
      volume24h = "$38.4B",
      high24h = "$65,400.00",
      low24h = "$62,900.00",
      isOtc = false
    ),
    MarketAsset(
      id = "eth_usdt",
      symbol = "ETH/USDT",
      name = "Ethereum",
      price = "$3,485.60",
      changePercentage = "+2.18%",
      isBullish = true,
      sparklinePoints = listOf(0.3f, 0.35f, 0.4f, 0.38f, 0.5f, 0.58f, 0.62f, 0.7f, 0.78f, 0.88f),
      assetType = "Crypto",
      volume24h = "$18.2B",
      high24h = "$3,520.00",
      low24h = "$3,390.00",
      isOtc = false
    ),
    MarketAsset(
      id = "sol_usdt",
      symbol = "SOL/USDT",
      name = "Solana",
      price = "$152.40",
      changePercentage = "+5.82%",
      isBullish = true,
      sparklinePoints = listOf(0.15f, 0.28f, 0.35f, 0.48f, 0.55f, 0.68f, 0.75f, 0.82f, 0.91f, 1.0f),
      assetType = "Crypto",
      volume24h = "$4.9B",
      high24h = "$155.10",
      low24h = "$144.20",
      isOtc = false
    ),

    // Forex
    MarketAsset(
      id = "eur_usd",
      symbol = "EUR/USD",
      name = "Euro / US Dollar",
      price = "1.08720",
      changePercentage = "+0.35%",
      isBullish = true,
      sparklinePoints = listOf(0.4f, 0.45f, 0.42f, 0.5f, 0.55f, 0.52f, 0.6f, 0.68f, 0.72f, 0.75f),
      assetType = "Forex",
      volume24h = "$124.5B",
      high24h = "1.08950",
      low24h = "1.08410",
      isOtc = false
    ),
    MarketAsset(
      id = "gbp_usd",
      symbol = "GBP/USD",
      name = "British Pound / USD",
      price = "1.29850",
      changePercentage = "-0.28%",
      isBullish = false,
      sparklinePoints = listOf(0.8f, 0.75f, 0.78f, 0.65f, 0.6f, 0.55f, 0.58f, 0.45f, 0.4f, 0.35f),
      assetType = "Forex",
      volume24h = "$92.1B",
      high24h = "1.30400",
      low24h = "1.29650",
      isOtc = false
    ),
    MarketAsset(
      id = "usd_jpy",
      symbol = "USD/JPY",
      name = "US Dollar / Yen",
      price = "154.620",
      changePercentage = "+0.45%",
      isBullish = true,
      sparklinePoints = listOf(0.35f, 0.4f, 0.38f, 0.45f, 0.52f, 0.6f, 0.68f, 0.72f, 0.8f, 0.85f),
      assetType = "Forex",
      volume24h = "$88.4B",
      high24h = "155.100",
      low24h = "154.020",
      isOtc = false
    ),

    // Indices
    MarketAsset(
      id = "spx_500",
      symbol = "SPX 500",
      name = "S&P 500 Index",
      price = "5,648.40",
      changePercentage = "+0.72%",
      isBullish = true,
      sparklinePoints = listOf(0.3f, 0.42f, 0.38f, 0.52f, 0.58f, 0.65f, 0.7f, 0.78f, 0.85f, 0.92f),
      assetType = "Indices",
      volume24h = "$45.2B",
      high24h = "5,662.00",
      low24h = "5,618.00",
      isOtc = false
    ),
    MarketAsset(
      id = "nasdaq_100",
      symbol = "NAS 100",
      name = "Nasdaq 100",
      price = "19,820.10",
      changePercentage = "+1.05%",
      isBullish = true,
      sparklinePoints = listOf(0.2f, 0.35f, 0.4f, 0.48f, 0.6f, 0.68f, 0.72f, 0.82f, 0.9f, 0.98f),
      assetType = "Indices",
      volume24h = "$38.9B",
      high24h = "19,890.00",
      low24h = "19,650.00",
      isOtc = false
    ),

    // Stocks
    MarketAsset(
      id = "aapl_stock",
      symbol = "AAPL",
      name = "Apple Inc.",
      price = "$228.40",
      changePercentage = "+1.42%",
      isBullish = true,
      sparklinePoints = listOf(0.35f, 0.42f, 0.4f, 0.55f, 0.62f, 0.58f, 0.72f, 0.8f, 0.88f, 0.94f),
      assetType = "Stocks",
      volume24h = "$12.8B",
      high24h = "$230.10",
      low24h = "$225.80",
      isOtc = false
    ),
    MarketAsset(
      id = "nvda_stock",
      symbol = "NVDA",
      name = "NVIDIA Corp.",
      price = "$124.60",
      changePercentage = "+4.15%",
      isBullish = true,
      sparklinePoints = listOf(0.2f, 0.32f, 0.45f, 0.4f, 0.58f, 0.68f, 0.75f, 0.85f, 0.92f, 1.0f),
      assetType = "Stocks",
      volume24h = "$26.4B",
      high24h = "$126.80",
      low24h = "$119.50",
      isOtc = false
    ),

    // OTC Assets (Quotex & Pocket Option)
    MarketAsset(
      id = "eur_otc",
      symbol = "EUR/USD (OTC)",
      name = "Euro / USD OTC",
      price = "1.08642",
      changePercentage = "+0.84%",
      isBullish = true,
      sparklinePoints = listOf(0.2f, 0.35f, 0.28f, 0.45f, 0.6f, 0.55f, 0.72f, 0.68f, 0.85f, 1.0f),
      assetType = "OTC",
      volume24h = "$14.8M",
      high24h = "1.08910",
      low24h = "1.08420",
      isOtc = true,
      otcPayout = "94%"
    ),
    MarketAsset(
      id = "gbp_otc",
      symbol = "GBP/JPY (OTC)",
      name = "GBP / JPY OTC",
      price = "192.410",
      changePercentage = "-0.45%",
      isBullish = false,
      sparklinePoints = listOf(0.85f, 0.75f, 0.80f, 0.65f, 0.60f, 0.45f, 0.52f, 0.38f, 0.32f, 0.20f),
      assetType = "OTC",
      volume24h = "$11.2M",
      high24h = "193.150",
      low24h = "192.080",
      isOtc = true,
      otcPayout = "93%"
    ),
    MarketAsset(
      id = "usd_inr",
      symbol = "USD/INR (OTC)",
      name = "USD / INR OTC",
      price = "83.6520",
      changePercentage = "+1.12%",
      isBullish = true,
      sparklinePoints = listOf(0.3f, 0.38f, 0.42f, 0.39f, 0.52f, 0.58f, 0.61f, 0.70f, 0.78f, 0.88f),
      assetType = "OTC",
      volume24h = "$18.5M",
      high24h = "83.8200",
      low24h = "83.4100",
      isOtc = true,
      otcPayout = "95%"
    ),
    MarketAsset(
      id = "btc_otc",
      symbol = "BTC/USD (OTC)",
      name = "Bitcoin OTC",
      price = "$64,820.50",
      changePercentage = "+3.42%",
      isBullish = true,
      sparklinePoints = listOf(0.25f, 0.38f, 0.45f, 0.42f, 0.55f, 0.68f, 0.72f, 0.80f, 0.90f, 0.96f),
      assetType = "OTC",
      volume24h = "$32.4M",
      high24h = "$65,240.00",
      low24h = "$62,810.00",
      isOtc = true,
      otcPayout = "91%"
    )
  )

  val activeSignals = listOf(
    // OTC Signal 1: Binary Options with CALL (UP) and Expiry
    TradingSignal(
      id = "sig_otc_1",
      pair = "EUR/USD (OTC)",
      type = SignalType.CALL,
      timeframe = "M1",
      expiryTime = "1 Min Expiry",
      entryPrice = "1.08642",
      confidencePercentage = 94,
      smcRationale = "Order Block Rejection",
      timestamp = "Just now",
      aiRationale = "Bullish Order Block (OB) mitigation at discount pricing on Quotex OTC feed. Impulsive green tick rejection confirms upward momentum.",
      payoutPercentage = "94%",
      isOtc = true,
      category = "OTC"
    ),

    // Crypto Signal 1: Traditional SL / TP
    TradingSignal(
      id = "sig_crypto_1",
      pair = "BTC/USDT",
      type = SignalType.BUY,
      timeframe = "1H",
      entryPrice = "$64,300.00",
      stopLoss = "$63,100.00",
      takeProfit = "$67,200.00",
      riskRewardRatio = "1:2.42",
      confidencePercentage = 93,
      smcRationale = "Bullish Liquidity Grab",
      timestamp = "3m ago",
      aiRationale = "Clean breakout of horizontal resistance on the 1H chart with rising spot volume. Stop loss placed tightly below swing low.",
      isOtc = false,
      category = "Crypto"
    ),

    // OTC Signal 2: Binary Options with PUT (DOWN) and Expiry
    TradingSignal(
      id = "sig_otc_2",
      pair = "GBP/JPY (OTC)",
      type = SignalType.PUT,
      timeframe = "M1",
      expiryTime = "1 Min Expiry",
      entryPrice = "192.410",
      confidencePercentage = 92,
      smcRationale = "FVG Fill + CHoCH",
      timestamp = "5m ago",
      aiRationale = "Premium Fair Value Gap (FVG) filled followed by Change of Character on Pocket Option OTC feed. Bearish displacement candle confirms downward binary flow.",
      payoutPercentage = "93%",
      isOtc = true,
      category = "OTC"
    ),

    // Forex Signal 1: Traditional SL / TP
    TradingSignal(
      id = "sig_forex_1",
      pair = "EUR/USD",
      type = SignalType.BUY,
      timeframe = "4H",
      entryPrice = "1.08450",
      stopLoss = "1.08050",
      takeProfit = "1.09650",
      riskRewardRatio = "1:3.00",
      confidencePercentage = 90,
      smcRationale = "Demand Zone Tap",
      timestamp = "8m ago",
      aiRationale = "Institutions absorbed liquidity at daily 0.618 Fibonacci level. Expecting bullish trend continuation into previous weekly highs.",
      isOtc = false,
      category = "Forex"
    ),

    // OTC Signal 3: Binary Options with CALL (UP) and Expiry
    TradingSignal(
      id = "sig_otc_3",
      pair = "USD/INR (OTC)",
      type = SignalType.CALL,
      timeframe = "M5",
      expiryTime = "5 Min Expiry",
      entryPrice = "83.6520",
      confidencePercentage = 96,
      smcRationale = "Liquidity Sweep + BOS",
      timestamp = "12m ago",
      aiRationale = "Sell-side liquidity swept beneath local range floor followed by aggressive Break of Structure (BOS) into high-demand institutional pool.",
      payoutPercentage = "95%",
      isOtc = true,
      category = "OTC"
    ),

    // Indices Signal 1: Traditional SL / TP
    TradingSignal(
      id = "sig_indices_1",
      pair = "SPX 500",
      type = SignalType.BUY,
      timeframe = "1H",
      entryPrice = "5,635.00",
      stopLoss = "5,605.00",
      takeProfit = "5,710.00",
      riskRewardRatio = "1:2.50",
      confidencePercentage = 89,
      smcRationale = "Bull Flag Breakout",
      timestamp = "15m ago",
      aiRationale = "S&P 500 consolidated above the 20-period EMA, completing an ascending wedge continuation with solid breadth expansion.",
      isOtc = false,
      category = "Indices"
    ),

    // Stocks Signal 1: Traditional SL / TP
    TradingSignal(
      id = "sig_stocks_1",
      pair = "NVDA",
      type = SignalType.BUY,
      timeframe = "1D",
      entryPrice = "$122.50",
      stopLoss = "$116.80",
      takeProfit = "$138.00",
      riskRewardRatio = "1:2.72",
      confidencePercentage = 91,
      smcRationale = "Institutional Accumulation",
      timestamp = "22m ago",
      aiRationale = "Strong volume accumulation on the daily chart with bullish RSI divergence. Clear path to test prior all-time resistance.",
      isOtc = false,
      category = "Stocks"
    )
  )

  fun getRefreshedAssets(baseAssets: List<MarketAsset>): List<MarketAsset> {
    return baseAssets.map { asset ->
      when {
        asset.id.contains("eur") -> {
          val base = if (asset.isOtc) 1.0860 else 1.0870
          val newPrice = base + (kotlin.random.Random.nextDouble() - 0.48) * 0.0025
          val change = 0.84 + (kotlin.random.Random.nextDouble() - 0.45) * 0.3
          val newPoints = asset.sparklinePoints.toMutableList().apply {
            if (isNotEmpty()) removeAt(0)
            add((lastOrNull() ?: 0.5f + (kotlin.random.Random.nextFloat() - 0.45f) * 0.15f).coerceIn(0.1f, 1.0f))
          }
          asset.copy(
            price = String.format(java.util.Locale.US, "%.5f", newPrice),
            changePercentage = String.format(java.util.Locale.US, "%+.2f%%", change),
            isBullish = change >= 0,
            sparklinePoints = newPoints
          )
        }
        asset.id.contains("btc") -> {
          val newPrice = 64800.0 + (kotlin.random.Random.nextDouble() - 0.45) * 350.0
          val change = 3.20 + (kotlin.random.Random.nextDouble() - 0.4) * 0.8
          val newPoints = asset.sparklinePoints.toMutableList().apply {
            if (isNotEmpty()) removeAt(0)
            add((lastOrNull() ?: 0.5f + (kotlin.random.Random.nextFloat() - 0.45f) * 0.15f).coerceIn(0.1f, 1.0f))
          }
          asset.copy(
            price = String.format(java.util.Locale.US, "$%,.2f", newPrice),
            changePercentage = String.format(java.util.Locale.US, "%+.2f%%", change),
            isBullish = change >= 0,
            sparklinePoints = newPoints
          )
        }
        asset.id.contains("gbp") -> {
          val base = if (asset.id.contains("jpy")) 192.40 else 1.2980
          val newPrice = base + (kotlin.random.Random.nextDouble() - 0.5) * (if (asset.id.contains("jpy")) 0.35 else 0.003)
          val change = -0.45 + (kotlin.random.Random.nextDouble() - 0.5) * 0.25
          val newPoints = asset.sparklinePoints.toMutableList().apply {
            if (isNotEmpty()) removeAt(0)
            add((lastOrNull() ?: 0.5f + (kotlin.random.Random.nextFloat() - 0.5f) * 0.15f).coerceIn(0.1f, 1.0f))
          }
          asset.copy(
            price = if (asset.id.contains("jpy")) String.format(java.util.Locale.US, "%.3f", newPrice) else String.format(java.util.Locale.US, "%.5f", newPrice),
            changePercentage = String.format(java.util.Locale.US, "%+.2f%%", change),
            isBullish = change >= 0,
            sparklinePoints = newPoints
          )
        }
        else -> {
          val delta = (kotlin.random.Random.nextFloat() - 0.48f) * 0.05f
          val newPoints = asset.sparklinePoints.toMutableList().apply {
            if (isNotEmpty()) removeAt(0)
            add(((lastOrNull() ?: 0.5f) + delta).coerceIn(0.1f, 1.0f))
          }
          asset.copy(sparklinePoints = newPoints)
        }
      }
    }
  }

  fun getRefreshedSignals(baseSignals: List<TradingSignal>): List<TradingSignal> {
    return baseSignals.mapIndexed { index, signal ->
      val newTimestamp = when (index) {
        0 -> "Just now"
        1 -> "1m ago"
        2 -> "4m ago"
        3 -> "9m ago"
        else -> "${(index + 1) * 4}m ago"
      }
      val confDelta = if (index % 2 == 0) 1 else -1
      signal.copy(
        timestamp = newTimestamp,
        confidencePercentage = (signal.confidencePercentage + confDelta).coerceIn(88, 98)
      )
    }
  }
}
