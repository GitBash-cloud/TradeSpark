import 'package:flutter/foundation.dart';

enum SignalType {
  buy('BUY / LONG', 'CALL (UP)', true),
  sell('SELL / SHORT', 'PUT (DOWN)', false),
  call('CALL (UP)', 'CALL (UP)', true),
  put('PUT (DOWN)', 'PUT (DOWN)', false);

  final String label;
  final String binaryLabel;
  final bool isBullish;

  const SignalType(this.label, this.binaryLabel, this.isBullish);
}

@immutable
class MarketAsset {
  final String id;
  final String symbol;
  final String name;
  final String price;
  final String changePercentage;
  final bool isBullish;
  final List<double> sparklinePoints;
  final String assetType;
  final String volume24h;
  final bool isOtc;
  final String otcPayout;

  const MarketAsset({
    required this.id,
    required this.symbol,
    required this.name,
    required this.price,
    required this.changePercentage,
    required this.isBullish,
    required this.sparklinePoints,
    this.assetType = 'Crypto',
    this.volume24h = '\$28.4B',
    this.isOtc = false,
    this.otcPayout = '92%',
  });
}

@immutable
class TradingSignal {
  final String id;
  final String pair;
  final SignalType type;
  final String timeframe;
  final String entryPrice;
  final String stopLoss;
  final String takeProfit;
  final String riskRewardRatio;
  final String expiryTime;
  final int confidencePercentage;
  final String smcRationale;
  final String timestamp;
  final String aiRationale;
  final String payoutPercentage;
  final bool isOtc;
  final String category;

  const TradingSignal({
    required this.id,
    required this.pair,
    required this.type,
    this.timeframe = '1H',
    required this.entryPrice,
    this.stopLoss = '',
    this.takeProfit = '',
    this.riskRewardRatio = '',
    this.expiryTime = '1 Min Expiry',
    this.confidencePercentage = 94,
    this.smcRationale = 'Order Block Rejection',
    this.timestamp = 'Just now',
    required this.aiRationale,
    this.payoutPercentage = '94%',
    this.isOtc = false,
    this.category = 'Forex',
  });
}
