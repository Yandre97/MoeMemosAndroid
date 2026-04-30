package me.mudkip.moememos.ui.page.memoinput

import androidx.compose.ui.text.TextStyle
import me.mudkip.moememos.ui.theme.MemoContentFontFamily
import org.junit.Assert.assertEquals
import org.junit.Test

class MemoInputTextStyleTest {
    @Test
    fun memoInputTextStyle_appliesWenKaiFontFamily() {
        val style = memoInputTextStyle(TextStyle.Default)

        assertEquals(MemoContentFontFamily, style.fontFamily)
    }
}
