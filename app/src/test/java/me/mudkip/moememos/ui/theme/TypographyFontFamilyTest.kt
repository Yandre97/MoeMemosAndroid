package me.mudkip.moememos.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class TypographyFontFamilyTest {
    @Test
    fun materialTypography_usesUiFontFamily() {
        assertEquals(UiFontFamily, Typography.bodyLarge.fontFamily)
        assertEquals(UiFontFamily, Typography.titleLarge.fontFamily)
        assertEquals(UiFontFamily, Typography.headlineLarge.fontFamily)
        assertEquals(UiFontFamily, Typography.labelLarge.fontFamily)
    }

    @Test
    fun memoContentFontFamily_remainsSeparatedFromUiFontFamily() {
        assertNotEquals(UiFontFamily, MemoContentFontFamily)
    }
}
