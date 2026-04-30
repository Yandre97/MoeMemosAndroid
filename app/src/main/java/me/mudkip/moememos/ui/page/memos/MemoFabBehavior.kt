package me.mudkip.moememos.ui.page.memos

private const val MEMO_FAB_COLLAPSE_THRESHOLD_PX = 48

fun shouldExpandMemoFab(
    firstVisibleItemIndex: Int,
    firstVisibleItemScrollOffset: Int
): Boolean {
    return firstVisibleItemIndex == 0 &&
        firstVisibleItemScrollOffset <= MEMO_FAB_COLLAPSE_THRESHOLD_PX
}
