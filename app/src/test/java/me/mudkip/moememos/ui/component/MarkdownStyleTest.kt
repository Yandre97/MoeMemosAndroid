package me.mudkip.moememos.ui.component

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import me.mudkip.moememos.ui.theme.MemoContentFontFamily
import org.junit.Assert.assertEquals
import org.junit.Test

class MarkdownStyleTest {
    @Test
    fun memoContentTextStyle_appliesWenKaiFontAndTextAlign() {
        val baseStyle = TextStyle.Default

        val style = memoContentTextStyle(baseStyle, TextAlign.Center)

        assertEquals(MemoContentFontFamily, style.fontFamily)
        assertEquals(TextAlign.Center, style.textAlign)
    }

    @Test
    fun memoContentTextStyle_keepsOriginalTextAlignWhenUnset() {
        val baseStyle = TextStyle(textAlign = TextAlign.End)

        val style = memoContentTextStyle(baseStyle, null)

        assertEquals(MemoContentFontFamily, style.fontFamily)
        assertEquals(TextAlign.End, style.textAlign)
    }
}
