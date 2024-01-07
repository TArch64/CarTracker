package screens.events.list

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import kotlin.math.max

@Composable
fun EventsScroller(
    initialIndex: Int,
    batch: Int,
    content: @Composable (index: Int) -> Unit
) {
    val rowState = rememberLazyListState(initialIndex)

    val initializedIndexes = remember {
        mutableStateListOf<Int>().apply {
            val startIndex = max(initialIndex - batch, 0)
            val endIndex = initialIndex + batch
            addAll(startIndex..endIndex)
        }
    }

    LazyRow(state = rowState) {
        items(items = initializedIndexes, key = { it }) { index ->
            if (index != 0 && index == initializedIndexes.first()) {
                val startIndex = max(index - batch - 1, 0)
                val endIndex = max(index - 1, 0)
                initializedIndexes.addAll(startIndex..endIndex)
            }

            content(index)

            if (index == initializedIndexes.last()) {
                val startIndex = index + 1
                val endIndex = index + 1 + batch
                initializedIndexes.addAll(startIndex..endIndex)
            }
        }
    }
}
