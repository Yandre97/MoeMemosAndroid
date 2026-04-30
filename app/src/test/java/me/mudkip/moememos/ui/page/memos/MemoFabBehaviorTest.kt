package me.mudkip.moememos.ui.page.memos

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MemoFabBehaviorTest {
    @Test
    fun staysExpanded_whenOnlySlightlyScrolledFromTop() {
        assertTrue(shouldExpandMemoFab(firstVisibleItemIndex = 0, firstVisibleItemScrollOffset = 24))
    }

    @Test
    fun collapses_afterPassingTopScrollThreshold() {
        assertFalse(shouldExpandMemoFab(firstVisibleItemIndex = 0, firstVisibleItemScrollOffset = 80))
    }

    @Test
    fun collapses_whenFirstItemIsNoLongerVisible() {
        assertFalse(shouldExpandMemoFab(firstVisibleItemIndex = 1, firstVisibleItemScrollOffset = 0))
    }
}
