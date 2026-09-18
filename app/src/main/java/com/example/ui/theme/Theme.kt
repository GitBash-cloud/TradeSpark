package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

val TradeSparkDarkColorScheme = darkColorScheme(
  primary = ProfitGreen,
  onPrimary = TextWhite,
  primaryContainer = ProfitGreenBg,
  onPrimaryContainer = ProfitGreenBright,
  secondary = LossRed,
  onSecondary = TextWhite,
  secondaryContainer = LossRedBg,
  onSecondaryContainer = LossRedBright,
  tertiary = AccentCyan,
  onTertiary = TextWhite,
  background = DarkBackground,
  onBackground = TextWhite,
  surface = DarkSurface,
  onSurface = TextWhite,
  surfaceVariant = DarkSurfaceElevated,
  onSurfaceVariant = TextMuted,
  outline = DarkBorder,
  outlineVariant = DarkBorderSubtle,
  error = LossRed,
  onError = TextWhite,
)

@Composable
fun TradeSparkTheme(
  darkTheme: Boolean = true,
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = TradeSparkDarkColorScheme,
    typography = Typography,
    content = content,
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  TradeSparkTheme(darkTheme = true, content = content)
}

