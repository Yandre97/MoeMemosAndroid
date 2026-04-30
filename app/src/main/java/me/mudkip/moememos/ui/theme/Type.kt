package me.mudkip.moememos.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.mudkip.moememos.R

val UiFontFamily = FontFamily(
        Font(R.font.misans_regular, FontWeight.Normal),
        Font(R.font.misans_medium, FontWeight.Medium),
        Font(R.font.misans_semibold, FontWeight.SemiBold),
        Font(R.font.misans_bold, FontWeight.Bold)
)

val MemoContentFontFamily = FontFamily(
        Font(R.font.lxgw_wenkai_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
        bodySmall = TextStyle(
                fontFamily = UiFontFamily
        ),
        bodyMedium = TextStyle(
                fontFamily = UiFontFamily
        ),
        bodyLarge = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
        ),
        titleSmall = TextStyle(
                fontFamily = UiFontFamily
        ),
        titleLarge = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp
        ),
        headlineLarge = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp
        ),
        headlineSmall = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp
        ),
        labelLarge = TextStyle(
                fontFamily = UiFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp
        )
)
