package me.mudkip.moememos.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun MoeMemosTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            WindowCompat.getInsetsController((view.context as Activity).window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = MoeMemosShapes,
            content = content
    )
}
