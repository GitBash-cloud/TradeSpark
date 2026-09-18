import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import '../models/trading_models.dart';
import '../theme/app_theme.dart';

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
            alignment: Alignment.Center,
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
