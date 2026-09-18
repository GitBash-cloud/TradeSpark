import 'package:flutter/material.dart';

class AppTheme {
  // Background & Surfaces
  static const Color darkBackground = Color(0xFF0B0E14);
  static const Color darkSurface = Color(0xFF161C24);
  static const Color darkSurfaceElevated = Color(0xFF1E2632);
  static const Color darkBorder = Color(0xFF263242);

  // Status & Trading Colors
  static const Color profitGreen = Color(0xFF00E676);
  static const Color profitGreenBg = Color(0x1F00E676);
  static const Color profitGreenBright = Color(0xFF00E676);
  static const Color lossRed = Color(0xFFFF1744);
  static const Color lossRedBg = Color(0x1FFF1744);
  static const Color lossRedBright = Color(0xFFFF1744);

  // Accents
  static const Color accentCyan = Color(0xFF00B8D9);
  static const Color accentGold = Color(0xFFFFAB00);

  // Typography
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
        background: darkBackground,
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
      textTheme: const TextTheme(
        bodyLarge: TextStyle(color: textWhite),
        bodyMedium: TextStyle(color: textWhite),
        bodySmall: TextStyle(color: textMuted),
      ),
    );
  }
}
