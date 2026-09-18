import 'package:flutter/material.dart';
import '../models/trading_models.dart';
import '../theme/app_theme.dart';

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
