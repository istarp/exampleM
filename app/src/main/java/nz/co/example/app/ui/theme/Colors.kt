package nz.co.example.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal data class AppColors(
    val accent: Accent,
    val background: Background,
    val foreground: Foreground,
    val icon: Icon
)

internal interface Accent {
    val primary: Color
}

internal interface Background {
    val primary: Color
    val secondary: Color
    val tertiary: Color
    val bottomNavigation: Color
}

internal interface Foreground {
    val primary: Color
    val secondary: Color
    val tertiary: Color
    val separator: Color
    val onPrimary: Color
}

internal interface Icon {
    val primary: Color
    val secondary: Color
    val tertiary: Color
}

internal val LightColors = AppColors(
    accent = object : Accent {
        override val primary = Color(0xFF0000FF)
    },
    background = object : Background {
        override val primary = Color(0xFFF4F4F9)
        override val secondary = Color(0xFFE7E7EC)
        override val tertiary = Color(0xFFFFFFFF)
        override val bottomNavigation = Color(0xFFFFFFFF)
    },
    foreground = object : Foreground {
        override val primary = Color(0xFF000000)
        override val secondary = Color(0xFF666666)
        override val tertiary = Color(0xFFB3B3B3)
        override val separator = Color(0xFFF0F0F0)
        override val onPrimary = Color(0xFFFFFFFF)

    },
    icon = object : Icon {
        override val primary = Color(0xFF000000)
        override val secondary = Color(0xFFB3B3B3)
        override val tertiary = Color(0xFF0000FF)
    }
)

internal val DarkColors = AppColors(
    accent = object : Accent {
        override val primary = Color(0xFF9595FE)
    },
    background = object : Background {
        override val primary = Color(0xFF181819)
        override val secondary = Color(0xFF2E2E2F)
        override val tertiary = Color(0xFF474747)
        override val bottomNavigation = Color(0xFF2E2E2F)
    },
    foreground = object : Foreground {
        override val primary = Color(0xFFFFFFFF)
        override val secondary = Color(0xFF8B8B8C)
        override val tertiary = Color(0xFF5D5D5E)
        override val separator = Color(0xFF474749)
        override val onPrimary = Color(0xFF000000)

    },
    icon = object : Icon {
        override val primary = Color(0xFFFFFFFF)
        override val secondary = Color(0xFFB3B3B3)
        override val tertiary = Color(0xFF9595FE)
    }
)

internal val LocalColors = staticCompositionLocalOf { LightColors }