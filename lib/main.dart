import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

// ==========================================
// 1. DATA MODELS
// ==========================================

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

// ==========================================
// 2. THEME DEFINITION
// ==========================================

class AppTheme {
  // Backgrounds & Surface
  static const Color darkBackground = Color(0xFF0B0E14);
  static const Color darkSurface = Color(0xFF161C24);
  static const Color darkSurfaceElevated = Color(0xFF1E2632);
  static const Color darkBorder = Color(0xFF263242);

  // Status & Signals
  static const Color profitGreen = Color(0xFF00E676);
  static const Color profitGreenBg = Color(0x1F00E676);
  static const Color lossRed = Color(0xFFFF1744);
  static const Color lossRedBg = Color(0x1FFF1744);

  // Accents
  static const Color accentCyan = Color(0xFF00B8D9);
  static const Color accentGold = Color(0xFFFFAB00);

  // Typography Colors
  static const Color textWhite = Color(0xFFFFFFFF);
  static const Color textMuted = Color(0xFF919EAB);
  static const Color textSubtle = Color(0xFF637381);

  static ThemeData get darkTheme {
    return ThemeData.dark().copyWith(
      scaffoldBackgroundColor: darkBackground,
      cardColor: darkSurface,
      colorScheme: const ColorScheme.dark(
        primary: profitGreen,
        secondary: accentCyan,
        surface: darkSurface,
        error: lossRed,
      ),
      appBarTheme: const AppBarTheme(
        backgroundColor: darkSurface,
        elevation: 0,
        centerTitle: false,
        titleTextStyle: TextStyle(
          color: textWhite,
          fontSize: 18,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}

// ==========================================
// 3. MAIN ENTRY POINT
// ==========================================

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setSystemUIOverlayStyle(
    const SystemUiOverlayStyle(
      statusBarColor: Colors.transparent,
      statusBarIconBrightness: Brightness.light,
      systemNavigationBarColor: AppTheme.darkBackground,
      systemNavigationBarIconBrightness: Brightness.light,
    ),
  );
  runApp(const TradeSparkApp());
}

class TradeSparkApp extends StatelessWidget {
  const TradeSparkApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'TradeSpark',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.darkTheme,
      home: const DashboardScreen(),
    );
  }
}

// ==========================================
// 4. DASHBOARD SCREEN
// ==========================================

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

// ==========================================
// 5. SCANNER SCREEN
// ==========================================

enum ScannerMode {
  forex('Standard Forex'),
  otcBinary('OTC Binary (Quotex/PO)');

  final String label;
  const ScannerMode(this.label);
}

class ScannerScreen extends StatefulWidget {
  final Function(TradingSignal) onApplySignal;

  const ScannerScreen({super.key, required this.onApplySignal});

  @override
  State<ScannerScreen> createState() => _ScannerScreenState();
}

class _ScannerScreenState extends State<ScannerScreen>
    with SingleTickerProviderStateMixin {
  ScannerMode _selectedMode = ScannerMode.forex;
  String _selectedExpiry = '1 Min';
  bool _isScanning = false;
  late AnimationController _laserController;

  @override
  void initState() {
    super.initState();
    _laserController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 1800),
    )..repeat(reverse: true);
  }

  @override
  void dispose() {
    _laserController.dispose();
    super.dispose();
  }

  void _triggerScan() {
    setState(() => _isScanning = true);
    Future.delayed(const Duration(milliseconds: 1200), () {
      if (mounted) {
        setState(() => _isScanning = false);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppTheme.darkBackground,
      appBar: AppBar(
        title: const Text('TradeSpark AI Vision Scanner'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // 1. Exact Header & Subtext
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: AppTheme.darkSurface,
                borderRadius: BorderRadius.circular(16),
                border: Border.all(color: AppTheme.darkBorder),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Container(
                        width: 38,
                        height: 38,
                        decoration: BoxDecoration(
                          color: AppTheme.profitGreenBg,
                          shape: BoxShape.circle,
                          border: Border.all(color: AppTheme.profitGreen),
                        ),
                        child: const Icon(Icons.camera_alt, color: AppTheme.profitGreen, size: 20),
                      ),
                      const SizedBox(width: 12),
                      const Expanded(
                        child: Text(
                          'Scan Chart & Get AI Insights',
                          style: TextStyle(
                            color: AppTheme.textWhite,
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 8),
                  const Text(
                    'Instant candlestick detection, support/resistance levels & automated SL/TP setup.',
                    style: TextStyle(color: AppTheme.textMuted, fontSize: 13, height: 1.4),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 16),

            // 2. Subtle Mode Toggle: ["Standard Forex" | "OTC Binary (Quotex/PO)"]
            Container(
              padding: const EdgeInsets.all(3),
              decoration: BoxDecoration(
                color: AppTheme.darkSurface,
                borderRadius: BorderRadius.circular(12),
                border: Border.all(color: AppTheme.darkBorder),
              ),
              child: Row(
                children: ScannerMode.values.map((mode) {
                  final isSelected = _selectedMode == mode;
                  return Expanded(
                    child: GestureDetector(
                      onTap: () {
                        setState(() => _selectedMode = mode);
                        _triggerScan();
                      },
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 10),
                        decoration: BoxDecoration(
                          color: isSelected ? const Color(0xFF00875A) : Colors.transparent,
                          borderRadius: BorderRadius.circular(9),
                          border: Border.all(
                            color: isSelected ? AppTheme.profitGreen : Colors.transparent,
                          ),
                        ),
                        alignment: Alignment.center,
                        child: Text(
                          mode.label,
                          style: TextStyle(
                            color: isSelected ? Colors.white : AppTheme.textMuted,
                            fontSize: 13,
                            fontWeight: isSelected ? FontWeight.bold : FontWeight.w500,
                          ),
                        ),
                      ),
                    ),
                  );
                }).toList(),
              ),
            ),
            const SizedBox(height: 16),

            // 3. Viewfinder Area with Candlesticks & Animated Laser Sweep
            Container(
              height: 180,
              decoration: BoxDecoration(
                color: AppTheme.darkSurface,
                borderRadius: BorderRadius.circular(16),
                border: Border.all(color: const Color(0xFF1E2838)),
              ),
              child: Stack(
                children: [
                  AnimatedBuilder(
                    animation: _laserController,
                    builder: (context, child) {
                      return CustomPaint(
                        size: Size.infinite,
                        painter: ScannerCandlePainter(laserProgress: _laserController.value),
                      );
                    },
                  ),
                  Positioned(
                    top: 10,
                    right: 10,
                    child: Container(
                      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                      decoration: BoxDecoration(
                        color: Colors.black87,
                        borderRadius: BorderRadius.circular(6),
                      ),
                      child: Text(
                        _selectedMode == ScannerMode.forex ? 'EUR/USD • 1H' : 'EUR/USD (OTC) • M1',
                        style: const TextStyle(color: Colors.white, fontSize: 11, fontWeight: FontWeight.bold),
                      ),
                    ),
                  ),
                  Positioned(
                    bottom: 10,
                    left: 10,
                    child: Container(
                      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                      decoration: BoxDecoration(
                        color: AppTheme.profitGreenBg,
                        borderRadius: BorderRadius.circular(8),
                        border: Border.all(color: AppTheme.profitGreen.withOpacity(0.5)),
                      ),
                      child: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          const Icon(Icons.verified, color: AppTheme.profitGreen, size: 14),
                          const SizedBox(width: 5),
                          Text(
                            _selectedMode == ScannerMode.forex
                                ? 'Bull Flag Breakout + S/R Retest Detected'
                                : 'Order Block Rejection + FVG Fill Detected',
                            style: const TextStyle(
                              color: AppTheme.profitGreen,
                              fontSize: 12,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 16),

            // 4. Dual Analysis Engine Display
            if (_selectedMode == ScannerMode.forex) ...[
              _buildForexAnalysisEngine(),
            ] else ...[
              _buildOtcBinaryAnalysisEngine(),
            ],
            const SizedBox(height: 18),

            // 5. Action Buttons: "Scan Chart Now" alongside upload icon button
            Row(
              children: [
                Expanded(
                  child: SizedBox(
                    height: 50,
                    child: ElevatedButton.icon(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: const Color(0xFF00875A),
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                      ),
                      onPressed: _triggerScan,
                      icon: _isScanning
                          ? const SizedBox(
                              width: 18,
                              height: 18,
                              child: CircularProgressIndicator(strokeWidth: 2, color: Colors.white),
                            )
                          : const Icon(Icons.camera_alt, size: 18, color: Colors.white),
                      label: Text(
                        _isScanning ? 'Scanning Candlesticks...' : 'Scan Chart Now',
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 15,
                          color: Colors.white,
                        ),
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 12),
                GestureDetector(
                  onTap: _triggerScan,
                  child: Container(
                    width: 50,
                    height: 50,
                    decoration: BoxDecoration(
                      color: const Color(0xFF222B38),
                      borderRadius: BorderRadius.circular(12),
                      border: Border.all(color: const Color(0xFF2E3B4D)),
                    ),
                    child: const Icon(Icons.file_upload, color: AppTheme.textWhite, size: 22),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),

            // 6. Signal Deployment Action
            SizedBox(
              height: 48,
              child: ElevatedButton.icon(
                style: ElevatedButton.styleFrom(
                  backgroundColor: _selectedMode == ScannerMode.forex
                      ? const Color(0xFF1E3A5F)
                      : const Color(0xFF00875A),
                  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                ),
                onPressed: () {
                  final isForex = _selectedMode == ScannerMode.forex;
                  final sig = TradingSignal(
                    id: 'sig_${DateTime.now().millisecondsSinceEpoch}',
                    pair: isForex ? 'EUR/USD' : 'EUR/USD (OTC)',
                    type: isForex ? SignalType.buy : SignalType.call,
                    timeframe: isForex ? '1H' : 'M1',
                    entryPrice: isForex ? '1.08450' : '1.08640',
                    stopLoss: isForex ? '1.08120' : '',
                    takeProfit: isForex ? '1.09250' : '',
                    riskRewardRatio: isForex ? '1:2.52' : '',
                    expiryTime: '$_selectedExpiry Expiry',
                    confidencePercentage: isForex ? 94 : 95,
                    smcRationale: isForex ? 'Bull Flag Breakout' : 'Order Block Rejection',
                    aiRationale: isForex
                        ? 'Technical AI diagnosis identified clean ascending channel breakout with 1.08450 demand zone confirmation.'
                        : 'Quotex/PO OTC feed retested bullish Order Block at key discount level with Fair Value Gap (FVG) mitigation.',
                    isOtc: !isForex,
                    category: isForex ? 'Forex' : 'OTC',
                  );
                  widget.onApplySignal(sig);
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content: Text('Deployed ${sig.pair} Setup to Active Feed!'),
                      backgroundColor: AppTheme.darkSurface,
                    ),
                  );
                },
                icon: const Icon(Icons.auto_awesome, size: 18, color: Colors.white),
                label: Text(
                  _selectedMode == ScannerMode.forex ? 'Deploy Forex Signal' : 'Deploy OTC Binary Signal',
                  style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 14, color: Colors.white),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildForexAnalysisEngine() {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: AppTheme.darkSurface,
        borderRadius: BorderRadius.circular(14),
        border: Border.all(color: AppTheme.darkBorder),
      ),
      child: Column(
        children: [
          const Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  Icon(Icons.auto_awesome, color: AppTheme.accentCyan, size: 16),
                  SizedBox(width: 8),
                  Text(
                    'Technical Forex Diagnosis',
                    style: TextStyle(color: Colors.white, fontSize: 13, fontWeight: FontWeight.bold),
                  ),
                ],
              ),
              Text(
                '93.8% Win Rate',
                style: TextStyle(color: AppTheme.accentCyan, fontSize: 12, fontWeight: FontWeight.bold),
              ),
            ],
          ),
          const SizedBox(height: 12),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
            decoration: BoxDecoration(
              color: AppTheme.darkBackground,
              borderRadius: BorderRadius.circular(10),
              border: Border.all(color: AppTheme.darkBorder.withOpacity(0.7)),
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                _metricBox('SIGNAL', 'BUY / LONG', AppTheme.profitGreen),
                _separator(),
                _metricBox('ENTRY', '1.08450', AppTheme.textWhite),
                _separator(),
                _metricBox('STOP LOSS', '1.08120', AppTheme.lossRed),
                _separator(),
                _metricBox('TAKE PROFIT', '1.09250', AppTheme.profitGreen),
              ],
            ),
          ),
          const SizedBox(height: 10),
          const Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                'Risk-to-Reward Ratio: 1:2.52',
                style: TextStyle(color: AppTheme.accentGold, fontSize: 12, fontWeight: FontWeight.bold),
              ),
              Text(
                'Timeframe: 1H Chart',
                style: TextStyle(color: AppTheme.textSubtle, fontSize: 12),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildOtcBinaryAnalysisEngine() {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: AppTheme.darkSurface,
        borderRadius: BorderRadius.circular(14),
        border: Border.all(color: AppTheme.darkBorder),
      ),
      child: Column(
        children: [
          const Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  Icon(Icons.psychology, color: AppTheme.accentCyan, size: 16),
                  SizedBox(width: 8),
                  Text(
                    'SMC Binary Diagnosis (Quotex/PO)',
                    style: TextStyle(color: Colors.white, fontSize: 13, fontWeight: FontWeight.bold),
                  ),
                ],
              ),
              Text(
                '95.4% Win Probability',
                style: TextStyle(color: AppTheme.accentCyan, fontSize: 12, fontWeight: FontWeight.bold),
              ),
            ],
          ),
          const SizedBox(height: 12),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              const Text('Contract Expiry:', style: TextStyle(color: AppTheme.textMuted, fontSize: 12, fontWeight: FontWeight.w500)),
              Row(
                children: ['1 Min', '3 Min', '5 Min'].map((exp) {
                  final isSelected = _selectedExpiry == exp;
                  return GestureDetector(
                    onTap: () => setState(() => _selectedExpiry = exp),
                    child: Container(
                      margin: const EdgeInsets.only(left: 8),
                      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                      decoration: BoxDecoration(
                        color: isSelected ? const Color(0xFF00875A) : AppTheme.darkBackground,
                        borderRadius: BorderRadius.circular(8),
                        border: Border.all(color: isSelected ? AppTheme.profitGreen : AppTheme.darkBorder),
                      ),
                      child: Text(
                        exp,
                        style: TextStyle(
                          color: isSelected ? Colors.white : AppTheme.textMuted,
                          fontSize: 11,
                          fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
                        ),
                      ),
                    ),
                  );
                }).toList(),
              ),
            ],
          ),
          const SizedBox(height: 12),
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
            decoration: BoxDecoration(
              color: AppTheme.darkBackground,
              borderRadius: BorderRadius.circular(10),
              border: Border.all(color: AppTheme.darkBorder.withOpacity(0.7)),
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                _metricBox('DIRECTION', 'CALL (UP)', AppTheme.profitGreen),
                _separator(),
                _metricBox('EXPIRY', '$_selectedExpiry Expiry', AppTheme.accentGold),
                _separator(),
                _metricBox('STRIKE ENTRY', '1.08640', AppTheme.textWhite),
                _separator(),
                _metricBox('EST. PAYOUT', '+94% Yield', AppTheme.profitGreen),
              ],
            ),
          ),
          const SizedBox(height: 10),
          const Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                'SMC Rationale: Order Block Rejection',
                style: TextStyle(color: AppTheme.accentCyan, fontSize: 12, fontWeight: FontWeight.bold),
              ),
              Text(
                'Feed: Pocket Option / Quotex',
                style: TextStyle(color: AppTheme.textSubtle, fontSize: 11),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _metricBox(String label, String value, Color color) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label,
          style: const TextStyle(color: AppTheme.textSubtle, fontSize: 9, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 2),
        Text(
          value,
          style: TextStyle(color: color, fontSize: 13, fontWeight: FontWeight.bold),
        ),
      ],
    );
  }

  Widget _separator() => Container(width: 1, height: 26, color: AppTheme.darkBorder);
}

class ScannerCandlePainter extends CustomPainter {
  final double laserProgress;
  ScannerCandlePainter({required this.laserProgress});

  @override
  void paint(Canvas canvas, Size size) {
    final w = size.width;
    final h = size.height;

    // Grid lines
    final gridPaint = Paint()
      ..color = const Color(0x14FFFFFF)
      ..strokeWidth = 1.0;
    for (int i = 1; i <= 4; i++) {
      final y = h * (i / 5.0);
      canvas.drawLine(Offset(0, y), Offset(w, y), gridPaint);
    }

    // Simulated Candlesticks
    const candleCount = 12;
    final spacing = w / (candleCount + 1);
    final candleHeights = [
      [0.4, 0.6], [0.45, 0.7], [0.35, 0.55], [0.5, 0.8],
      [0.48, 0.65], [0.6, 0.85], [0.58, 0.78], [0.7, 0.9],
      [0.65, 0.82], [0.72, 0.95], [0.8, 0.98], [0.75, 0.92],
    ];

    for (int i = 0; i < candleHeights.length; i++) {
      final cx = (i + 1) * spacing;
      final topY = h * (1.0 - candleHeights[i][1]);
      final bottomY = h * (1.0 - candleHeights[i][0]);
      final isGreen = i % 3 != 1;
      final cColor = isGreen ? AppTheme.profitGreen : AppTheme.lossRed;

      final wickPaint = Paint()
        ..color = cColor
        ..strokeWidth = 1.5;
      canvas.drawLine(Offset(cx, topY - 8), Offset(cx, bottomY + 8), wickPaint);

      final bodyPaint = Paint()
        ..color = cColor
        ..style = PaintingStyle.fill;
      canvas.drawRRect(
        RRect.fromRectAndRadius(
          Rect.fromCenter(center: Offset(cx, (topY + bottomY) / 2), width: 8, height: bottomY - topY),
          const Radius.circular(2),
        ),
        bodyPaint,
      );
    }

    // Scanning laser line
    final laserY = h * laserProgress;
    final laserPaint = Paint()
      ..shader = const LinearGradient(
        colors: [
          Colors.transparent,
          AppTheme.accentCyan,
          AppTheme.profitGreen,
          AppTheme.accentCyan,
          Colors.transparent,
        ],
      ).createShader(Rect.fromLTWH(0, laserY, w, 3))
      ..strokeWidth = 3.0;
    canvas.drawLine(Offset(0, laserY), Offset(w, laserY), laserPaint);
  }

  @override
  bool shouldRepaint(covariant ScannerCandlePainter oldDelegate) => oldDelegate.laserProgress != laserProgress;
}

// ==========================================
// 6. MARKET TICKER TAPE
// ==========================================

class MarketTickerTape extends StatelessWidget {
  final List<MarketAsset> assets;
  final String selectedCategory;
  final ValueChanged<String> onCategoryChanged;

  const MarketTickerTape({
    super.key,
    required this.assets,
    required this.selectedCategory,
    required this.onCategoryChanged,
  });

  static const List<String> categories = ['All', 'OTC', 'Crypto', 'Forex', 'Indices', 'Stocks'];

  @override
  Widget build(BuildContext context) {
    final filteredAssets = selectedCategory == 'All'
        ? assets
        : selectedCategory == 'OTC'
            ? assets.where((a) => a.isOtc).toList()
            : assets.where((a) => a.assetType.toLowerCase() == selectedCategory.toLowerCase()).toList();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // Category Filter Chips
        SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          padding: const EdgeInsets.symmetric(horizontal: 16),
          child: Row(
            children: categories.map((cat) {
              final isSelected = selectedCategory == cat;
              return GestureDetector(
                onTap: () => onCategoryChanged(cat),
                child: Container(
                  margin: const EdgeInsets.only(right: 8),
                  padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 7),
                  decoration: BoxDecoration(
                    color: isSelected ? const Color(0xFF00875A) : AppTheme.darkSurfaceElevated,
                    borderRadius: BorderRadius.circular(10),
                    border: Border.all(
                      color: isSelected ? AppTheme.profitGreen : AppTheme.darkBorder,
                    ),
                  ),
                  child: Text(
                    cat,
                    style: TextStyle(
                      color: isSelected ? Colors.white : AppTheme.textMuted,
                      fontSize: 12,
                      fontWeight: isSelected ? FontWeight.bold : FontWeight.w500,
                    ),
                  ),
                ),
              );
            }).toList(),
          ),
        ),
        const SizedBox(height: 12),

        // Horizontal Ticker Cards
        SizedBox(
          height: 94,
          child: ListView.separated(
            scrollDirection: Axis.horizontal,
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: filteredAssets.length,
            separatorBuilder: (_, __) => const SizedBox(width: 10),
            itemBuilder: (context, index) {
              final asset = filteredAssets[index];
              return Container(
                width: 150,
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: AppTheme.darkSurface,
                  borderRadius: BorderRadius.circular(12),
                  border: Border.all(color: AppTheme.darkBorder),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          asset.symbol,
                          style: const TextStyle(
                            color: AppTheme.textWhite,
                            fontSize: 13,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                          decoration: BoxDecoration(
                            color: asset.isOtc ? const Color(0xFF2A1C3D) : AppTheme.darkBackground,
                            borderRadius: BorderRadius.circular(4),
                            border: Border.all(
                              color: asset.isOtc ? const Color(0xFF9C27B0) : AppTheme.darkBorder,
                            ),
                          ),
                          child: Text(
                            asset.isOtc ? 'OTC' : asset.assetType.toUpperCase(),
                            style: TextStyle(
                              color: asset.isOtc ? const Color(0xFFCE93D8) : AppTheme.accentCyan,
                              fontSize: 9,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ],
                    ),
                    Text(
                      asset.price,
                      style: const TextStyle(
                        color: AppTheme.textWhite,
                        fontSize: 14,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Row(
                      children: [
                        Icon(
                          asset.isBullish ? Icons.trending_up : Icons.trending_down,
                          color: asset.isBullish ? AppTheme.profitGreen : AppTheme.lossRed,
                          size: 13,
                        ),
                        const SizedBox(width: 4),
                        Text(
                          asset.changePercentage,
                          style: TextStyle(
                            color: asset.isBullish ? AppTheme.profitGreen : AppTheme.lossRed,
                            fontSize: 11,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        if (asset.isOtc) ...[
                          const Spacer(),
                          Text(
                            asset.otcPayout,
                            style: const TextStyle(
                              color: AppTheme.accentGold,
                              fontSize: 10,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ],
                    ),
                  ],
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}

// ==========================================
// 7. QUICK ACTION BANNER
// ==========================================

class QuickActionBanner extends StatelessWidget {
  final VoidCallback onLaunchScanner;

  const QuickActionBanner({super.key, required this.onLaunchScanner});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: 16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        gradient: const LinearGradient(
          colors: [Color(0xFF13202E), Color(0xFF192534)],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: AppTheme.profitGreen.withOpacity(0.35)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Container(
                width: 8,
                height: 8,
                decoration: const BoxDecoration(
                  color: AppTheme.profitGreen,
                  shape: BoxShape.circle,
                ),
              ),
              const SizedBox(width: 6),
              const Text(
                'AI VISION SCANNER',
                style: TextStyle(
                  color: AppTheme.profitGreen,
                  fontSize: 10,
                  fontWeight: FontWeight.bold,
                  letterSpacing: 0.8,
                ),
              ),
            ],
          ),
          const SizedBox(height: 8),
          Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Scan Chart & Get AI Insights',
                      style: TextStyle(
                        color: AppTheme.textWhite,
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 4),
                    Text(
                      'Instant candlestick detection, support/resistance levels & automated SL/TP setup.',
                      style: TextStyle(
                        color: AppTheme.textMuted,
                        fontSize: 12,
                        height: 1.35,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(width: 12),
              GestureDetector(
                onTap: onLaunchScanner,
                child: Container(
                  width: 44,
                  height: 44,
                  decoration: BoxDecoration(
                    color: AppTheme.profitGreenBg,
                    shape: BoxShape.circle,
                    border: Border.all(color: AppTheme.profitGreen),
                  ),
                  child: const Icon(Icons.camera_alt, color: AppTheme.profitGreen, size: 22),
                ),
              ),
            ],
          ),
          const SizedBox(height: 14),
          SizedBox(
            width: double.infinity,
            height: 42,
            child: ElevatedButton.icon(
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color(0xFF00875A),
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
              ),
              onPressed: onLaunchScanner,
              icon: const Icon(Icons.camera_alt, size: 16, color: Colors.white),
              label: const Text(
                'Scan Chart Now',
                style: TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 13,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

// ==========================================
// 8. ACTIVE SIGNALS SECTION
// ==========================================

class ActiveSignalsSection extends StatefulWidget {
  final List<TradingSignal> signals;
  final Function(TradingSignal) onCopySignal;

  const ActiveSignalsSection({
    super.key,
    required this.signals,
    required this.onCopySignal,
  });

  @override
  State<ActiveSignalsSection> createState() => _ActiveSignalsSectionState();
}

class _ActiveSignalsSectionState extends State<ActiveSignalsSection> {
  String _selectedCategory = 'All';
  final List<String> _categories = ['All', 'OTC', 'Crypto', 'Forex', 'Indices', 'Stocks'];

  @override
  Widget build(BuildContext context) {
    final filtered = _selectedCategory == 'All'
        ? widget.signals
        : _selectedCategory == 'OTC'
            ? widget.signals.where((s) => s.isOtc).toList()
            : widget.signals.where((s) => s.category.toLowerCase() == _selectedCategory.toLowerCase()).toList();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // Category Filter Chips
        SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          padding: const EdgeInsets.symmetric(horizontal: 16),
          child: Row(
            children: _categories.map((cat) {
              final isSelected = _selectedCategory == cat;
              return GestureDetector(
                onTap: () => setState(() => _selectedCategory = cat),
                child: Container(
                  margin: const EdgeInsets.only(right: 8),
                  padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 7),
                  decoration: BoxDecoration(
                    color: isSelected ? const Color(0xFF00875A) : AppTheme.darkSurfaceElevated,
                    borderRadius: BorderRadius.circular(10),
                    border: Border.all(
                      color: isSelected ? AppTheme.profitGreen : AppTheme.darkBorder,
                    ),
                  ),
                  child: Text(
                    cat,
                    style: TextStyle(
                      color: isSelected ? Colors.white : AppTheme.textMuted,
                      fontSize: 12,
                      fontWeight: isSelected ? FontWeight.bold : FontWeight.w500,
                    ),
                  ),
                ),
              );
            }).toList(),
          ),
        ),
        const SizedBox(height: 12),

        // Signal Cards List
        if (filtered.isEmpty)
          Container(
            margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 20),
            padding: const EdgeInsets.all(24),
            alignment: Alignment.center,
            decoration: BoxDecoration(
              color: AppTheme.darkSurface,
              borderRadius: BorderRadius.circular(16),
              border: Border.all(color: AppTheme.darkBorder),
            ),
            child: const Column(
              children: [
                Icon(Icons.query_stats, color: AppTheme.textMuted, size: 36),
                SizedBox(height: 8),
                Text(
                  'No signals in this category',
                  style: TextStyle(color: AppTheme.textMuted, fontSize: 13),
                ),
              ],
            ),
          )
        else
          ListView.separated(
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: filtered.length,
            separatorBuilder: (_, __) => const SizedBox(height: 12),
            itemBuilder: (context, index) {
              final signal = filtered[index];
              return _SignalCard(
                signal: signal,
                onCopy: () => widget.onCopySignal(signal),
              );
            },
          ),
      ],
    );
  }
}

class _SignalCard extends StatefulWidget {
  final TradingSignal signal;
  final VoidCallback onCopy;

  const _SignalCard({required this.signal, required this.onCopy});

  @override
  State<_SignalCard> createState() => _SignalCardState();
}

class _SignalCardState extends State<_SignalCard> {
  bool _expanded = false;

  @override
  Widget build(BuildContext context) {
    final s = widget.signal;
    final isBullish = s.type.isBullish;
    final actionColor = isBullish ? AppTheme.profitGreen : AppTheme.lossRed;
    final actionBg = isBullish ? AppTheme.profitGreenBg : AppTheme.lossRedBg;
    final actionLabel = s.isOtc ? s.type.binaryLabel : s.type.label;

    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: AppTheme.darkSurface,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: AppTheme.darkBorder),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Header: Pair, Timeframe, Category Pill, Win Rate
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  Text(
                    s.pair,
                    style: const TextStyle(
                      color: Colors.white,
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(width: 8),
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                    decoration: BoxDecoration(
                      color: AppTheme.darkBackground,
                      borderRadius: BorderRadius.circular(4),
                      border: Border.all(color: AppTheme.darkBorder),
                    ),
                    child: Text(
                      s.timeframe,
                      style: const TextStyle(
                        color: AppTheme.textMuted,
                        fontSize: 10,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  const SizedBox(width: 6),
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                    decoration: BoxDecoration(
                      color: s.isOtc ? const Color(0xFF2A1C3D) : AppTheme.darkBackground,
                      borderRadius: BorderRadius.circular(4),
                      border: Border.all(
                        color: s.isOtc ? const Color(0xFF9C27B0) : AppTheme.darkBorder,
                      ),
                    ),
                    child: Text(
                      s.isOtc ? 'OTC' : s.category.toUpperCase(),
                      style: TextStyle(
                        color: s.isOtc ? const Color(0xFFCE93D8) : AppTheme.accentCyan,
                        fontSize: 9,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ],
              ),
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 3),
                decoration: BoxDecoration(
                  color: const Color(0x2200B8D9),
                  borderRadius: BorderRadius.circular(12),
                  border: Border.all(color: AppTheme.accentCyan.withOpacity(0.5)),
                ),
                child: Text(
                  '${s.confidencePercentage}% Accuracy',
                  style: const TextStyle(
                    color: AppTheme.accentCyan,
                    fontSize: 11,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(height: 10),

          // Direction Pill & Strategy/R:R Badge
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                decoration: BoxDecoration(
                  color: actionBg,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: actionColor.withOpacity(0.5)),
                ),
                child: Text(
                  actionLabel,
                  style: TextStyle(
                    color: actionColor,
                    fontSize: 13,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
              if (s.isOtc)
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 5),
                  decoration: BoxDecoration(
                    color: const Color(0xFF1E2838),
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(color: const Color(0xFF2C3E55)),
                  ),
                  child: Text(
                    s.smcRationale,
                    style: const TextStyle(
                      color: AppTheme.accentCyan,
                      fontSize: 11,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                )
              else
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 5),
                  decoration: BoxDecoration(
                    color: AppTheme.darkBackground,
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(color: AppTheme.darkBorder),
                  ),
                  child: Text(
                    'R:R ${s.riskRewardRatio.isNotEmpty ? s.riskRewardRatio : '1:2.5'}',
                    style: const TextStyle(
                      color: AppTheme.accentGold,
                      fontSize: 11,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ),
            ],
          ),
          const SizedBox(height: 12),

          // Parameters Matrix: Traditional (SL/TP) vs OTC Binary (Expiry/Yield)
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
            decoration: BoxDecoration(
              color: AppTheme.darkBackground,
              borderRadius: BorderRadius.circular(10),
              border: Border.all(color: AppTheme.darkBorder.withOpacity(0.7)),
            ),
            child: s.isOtc
                ? Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      _itemCol('STRIKE / ENTRY', s.entryPrice, Colors.white),
                      _vDivider(),
                      _itemCol('EXPIRY TIME', s.expiryTime, AppTheme.accentGold),
                      _vDivider(),
                      _itemCol('EST. PAYOUT', '${s.payoutPercentage} Return', AppTheme.profitGreen),
                    ],
                  )
                : Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      _itemCol('ENTRY', s.entryPrice, Colors.white),
                      _vDivider(),
                      _itemCol('STOP LOSS', s.stopLoss.isNotEmpty ? s.stopLoss : 'Dynamic', AppTheme.lossRed),
                      _vDivider(),
                      _itemCol('TAKE PROFIT', s.takeProfit.isNotEmpty ? s.takeProfit : 'Dynamic', AppTheme.profitGreen),
                    ],
                  ),
          ),
          const SizedBox(height: 12),

          // Collapsible Deep-Dive Analysis
          if (_expanded) ...[
            Container(
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: const Color(0xFF19222E),
                borderRadius: BorderRadius.circular(10),
                border: Border.all(color: const Color(0xFF263548)),
              ),
              child: Text(
                s.aiRationale,
                style: const TextStyle(color: AppTheme.textMuted, fontSize: 12, height: 1.4),
              ),
            ),
            const SizedBox(height: 12),
          ],

          // Card Footer: Toggle Analysis & Copy Button
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              GestureDetector(
                onTap: () => setState(() => _expanded = !_expanded),
                child: Text(
                  _expanded ? 'Hide Analysis' : 'View Analysis',
                  style: const TextStyle(
                    color: AppTheme.accentCyan,
                    fontSize: 12,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ),
              ElevatedButton.icon(
                style: ElevatedButton.styleFrom(
                  backgroundColor: AppTheme.darkSurfaceElevated,
                  foregroundColor: Colors.white,
                  side: const BorderSide(color: AppTheme.darkBorder),
                  shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                  padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 7),
                ),
                onPressed: widget.onCopy,
                icon: const Icon(Icons.copy, size: 14),
                label: const Text(
                  'Copy Setup',
                  style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _itemCol(String label, String value, Color color) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          label,
          style: const TextStyle(color: AppTheme.textSubtle, fontSize: 8, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 2),
        Text(
          value,
          style: TextStyle(color: color, fontSize: 12, fontWeight: FontWeight.bold),
        ),
      ],
    );
  }

  Widget _vDivider() => Container(width: 1, height: 26, color: AppTheme.darkBorder);
}
