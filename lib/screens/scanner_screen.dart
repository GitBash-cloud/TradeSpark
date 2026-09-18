import 'package:flutter/material.dart';
import '../models/trading_models.dart';
import '../theme/app_theme.dart';

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
            // 1. Core Header & Subtext (Strict Trade Buddy UI layout)
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
                        alignment: Alignment.Center,
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

            // 3. Viewfinder Area with Candlesticks & Animated Laser
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

            // 5. Original Primary Button: "Scan Chart Now" alongside upload icon button
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

    // Scanning laser
    final laserY = h * laserProgress;
    final laserPaint = Paint()
      ..shader = const LinearGradient(
        colors: [
          Colors.transparent,
          AppTheme.accentCyan,
          AppTheme.profitGreenBright,
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
