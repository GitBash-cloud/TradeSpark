import 'package:flutter/material.dart';
import '../models/trading_models.dart';
import '../theme/app_theme.dart';
import '../widgets/active_signals_section.dart';
import '../widgets/market_ticker_tape.dart';
import '../widgets/quick_action_banner.dart';
import 'scanner_screen.dart';

class DashboardScreen extends StatefulWidget {
  const DashboardScreen({super.key});

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  int _currentTabIndex = 0;
  String _selectedCategory = 'All';

  final List<MarketAsset> _marketAssets = const [
    MarketAsset(
      id: 'otc_eurusd',
      symbol: 'EUR/USD (OTC)',
      name: 'Euro / US Dollar OTC',
      price: '1.08640',
      changePercentage: '+0.84%',
      isBullish: true,
      sparklinePoints: [1.082, 1.083, 1.084, 1.0864],
      assetType: 'OTC',
      volume24h: '\$412M',
      isOtc: true,
      otcPayout: '94%',
    ),
    MarketAsset(
      id: 'otc_gbpusd',
      symbol: 'GBP/USD (OTC)',
      name: 'Pound / US Dollar OTC',
      price: '1.29410',
      changePercentage: '+1.12%',
      isBullish: true,
      sparklinePoints: [1.288, 1.291, 1.2941],
      assetType: 'OTC',
      volume24h: '\$318M',
      isOtc: true,
      otcPayout: '92%',
    ),
    MarketAsset(
      id: 'otc_usdjpy',
      symbol: 'USD/JPY (OTC)',
      name: 'Dollar / Yen OTC',
      price: '154.220',
      changePercentage: '-0.45%',
      isBullish: false,
      sparklinePoints: [154.9, 154.6, 154.22],
      assetType: 'OTC',
      volume24h: '\$295M',
      isOtc: true,
      otcPayout: '93%',
    ),
    MarketAsset(
      id: 'crypto_btc',
      symbol: 'BTC/USD',
      name: 'Bitcoin',
      price: '\$67,420.50',
      changePercentage: '+3.42%',
      isBullish: true,
      sparklinePoints: [65200, 66100, 67420.5],
      assetType: 'Crypto',
      volume24h: '\$28.4B',
      isOtc: false,
    ),
    MarketAsset(
      id: 'crypto_eth',
      symbol: 'ETH/USD',
      name: 'Ethereum',
      price: '\$3,510.20',
      changePercentage: '+2.18%',
      isBullish: true,
      sparklinePoints: [3410, 3480, 3510.2],
      assetType: 'Crypto',
      volume24h: '\$14.2B',
      isOtc: false,
    ),
    MarketAsset(
      id: 'forex_eurusd',
      symbol: 'EUR/USD',
      name: 'Euro / US Dollar',
      price: '1.08450',
      changePercentage: '+0.15%',
      isBullish: true,
      sparklinePoints: [1.082, 1.0835, 1.0845],
      assetType: 'Forex',
      volume24h: '\$5.1B',
      isOtc: false,
    ),
    MarketAsset(
      id: 'indices_nasdaq',
      symbol: 'NAS100',
      name: 'Nasdaq 100',
      price: '18,840.10',
      changePercentage: '+0.95%',
      isBullish: true,
      sparklinePoints: [18650, 18720, 18840.1],
      assetType: 'Indices',
      volume24h: '\$9.8B',
      isOtc: false,
    ),
    MarketAsset(
      id: 'stocks_nvda',
      symbol: 'NVDA',
      name: 'NVIDIA Corp',
      price: '\$124.80',
      changePercentage: '+4.12%',
      isBullish: true,
      sparklinePoints: [119.5, 122.0, 124.8],
      assetType: 'Stocks',
      volume24h: '\$18.5B',
      isOtc: false,
    ),
  ];

  late List<TradingSignal> _signals;

  @override
  void initState() {
    super.initState();
    _signals = [
      const TradingSignal(
        id: 'sig_otc_1',
        pair: 'EUR/USD (OTC)',
        type: SignalType.call,
        timeframe: 'M1',
        entryPrice: '1.08640',
        expiryTime: '1 Min Expiry',
        confidencePercentage: 95,
        smcRationale: 'Order Block Rejection',
        timestamp: '2 min ago',
        aiRationale: 'SMC Vision Scanner detected Order Block mitigation at key institutional demand level with Fair Value Gap (FVG) confirmation on 1-minute OTC chart.',
        payoutPercentage: '94%',
        isOtc: true,
        category: 'OTC',
      ),
      const TradingSignal(
        id: 'sig_otc_2',
        pair: 'GBP/USD (OTC)',
        type: SignalType.put,
        timeframe: 'M1',
        entryPrice: '1.29410',
        expiryTime: '3 Min Expiry',
        confidencePercentage: 93,
        smcRationale: 'FVG Imbalance Fill',
        timestamp: '5 min ago',
        aiRationale: 'Price reached bearish fair value gap with liquidity sweep at session highs. Premium discount equilibrium indicates downside continuation.',
        payoutPercentage: '92%',
        isOtc: true,
        category: 'OTC',
      ),
      const TradingSignal(
        id: 'sig_btc_1',
        pair: 'BTC/USD',
        type: SignalType.buy,
        timeframe: '1H',
        entryPrice: '\$66,800.00',
        stopLoss: '\$65,400.00',
        takeProfit: '\$70,200.00',
        riskRewardRatio: '1:2.42',
        confidencePercentage: 92,
        smcRationale: 'Bull Flag Breakout',
        timestamp: '12 min ago',
        aiRationale: 'Strong institutional accumulation above the 200 EMA with bullish volume confirmation.',
        isOtc: false,
        category: 'Crypto',
      ),
      const TradingSignal(
        id: 'sig_eur_1',
        pair: 'EUR/USD',
        type: SignalType.buy,
        timeframe: '4H',
        entryPrice: '1.08250',
        stopLoss: '1.07900',
        takeProfit: '1.09100',
        riskRewardRatio: '1:2.43',
        confidencePercentage: 88,
        smcRationale: 'Demand Zone Retest',
        timestamp: '28 min ago',
        aiRationale: 'London session liquidity sweep tested the weekly key support level before breaking local structure to the upside.',
        isOtc: false,
        category: 'Forex',
      ),
    ];
  }

  void _openScanner() {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (_) => ScannerScreen(
          onApplySignal: (newSignal) {
            setState(() {
              _signals.insert(0, newSignal);
            });
          },
        ),
      ),
    );
  }

  void _copySignal(TradingSignal signal) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text('Copied ${signal.pair} setup to clipboard!'),
        backgroundColor: AppTheme.darkSurface,
        behavior: SnackBarBehavior.floating,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppTheme.darkBackground,
      appBar: AppBar(
        title: Row(
          children: [
            Container(
              width: 28,
              height: 28,
              decoration: BoxDecoration(
                color: AppTheme.profitGreenBg,
                shape: BoxShape.circle,
                border: Border.all(color: AppTheme.profitGreen),
              ),
              child: const Icon(Icons.flash_on, color: AppTheme.profitGreen, size: 16),
            ),
            const SizedBox(width: 8),
            const Text(
              'TradeSpark',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
            ),
          ],
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.tune, color: AppTheme.textMuted),
            onPressed: () {},
          ),
          IconButton(
            icon: const Icon(Icons.notifications_active, color: AppTheme.profitGreen),
            onPressed: () {},
          ),
        ],
      ),
      body: IndexedStack(
        index: _currentTabIndex,
        children: [
          // Tab 0: Home / Dashboard
          SingleChildScrollView(
            padding: const EdgeInsets.only(top: 14, bottom: 32),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // 1. Market Ticker Tape (Forex, Crypto, Indices, Stocks, and dedicated OTC)
                MarketTickerTape(
                  assets: _marketAssets,
                  selectedCategory: _selectedCategory,
                  onCategoryChanged: (cat) => setState(() => _selectedCategory = cat),
                ),
                const SizedBox(height: 18),

                // 2. Quick Action Banner ("Scan Chart & Get AI Insights")
                QuickActionBanner(onLaunchScanner: _openScanner),
                const SizedBox(height: 20),

                // 3. Section Title
                const Padding(
                  padding: EdgeInsets.symmetric(horizontal: 16),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        'Active Signals Feed',
                        style: TextStyle(
                          color: AppTheme.textWhite,
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      Text(
                        'Live AI & SMC Engine',
                        style: TextStyle(
                          color: AppTheme.profitGreen,
                          fontSize: 12,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ),
                const SizedBox(height: 12),

                // 4. Active Signals List (Dual Traditional & OTC Binary Support)
                ActiveSignalsSection(
                  signals: _signals,
                  onCopySignal: _copySignal,
                ),
              ],
            ),
          ),

          // Tab 1: Signals Stream
          SingleChildScrollView(
            padding: const EdgeInsets.symmetric(vertical: 16),
            child: ActiveSignalsSection(
              signals: _signals,
              onCopySignal: _copySignal,
            ),
          ),

          // Tab 2: Vision Scanner Tab
          ScannerScreen(
            onApplySignal: (newSignal) {
              setState(() {
                _signals.insert(0, newSignal);
                _currentTabIndex = 0;
              });
            },
          ),
        ],
      ),
      bottomNavigationBar: Container(
        decoration: const BoxDecoration(
          color: AppTheme.darkSurface,
          border: Border(top: BorderSide(color: AppTheme.darkBorder)),
        ),
        child: BottomNavigationBar(
          backgroundColor: AppTheme.darkSurface,
          selectedItemColor: AppTheme.profitGreen,
          unselectedItemColor: AppTheme.textMuted,
          currentIndex: _currentTabIndex,
          onTap: (index) => setState(() => _currentTabIndex = index),
          items: const [
            BottomNavigationBarItem(
              icon: Icon(Icons.dashboard_outlined),
              activeIcon: Icon(Icons.dashboard),
              label: 'Dashboard',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.show_chart),
              activeIcon: Icon(Icons.auto_graph),
              label: 'Signals',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.camera_alt_outlined),
              activeIcon: Icon(Icons.camera_alt),
              label: 'Scanner',
            ),
          ],
        ),
      ),
    );
  }
}
