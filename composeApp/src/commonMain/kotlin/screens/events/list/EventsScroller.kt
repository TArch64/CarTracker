package screens.events.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import storage.repository.model.Mileage
import kotlin.math.max

@Composable
fun EventsScroller(
    initialMileage: Mileage,
    initialIndexShift: Int = 1,
    batch: Int,
    content: @Composable (mileage: Mileage) -> Unit
) {
    val rowState = rememberLazyListState(max(initialMileage.index - initialIndexShift, 0))

    fun mileagesByRange(fromIndex: Int, toIndex: Int): List<Mileage> {
        return (fromIndex..toIndex).map(Mileage.Companion::byIndex)
    }

    val mileages = remember {
        val mileages = mileagesByRange(0, initialMileage.index + batch)
        mutableStateListOf(*mileages.toTypedArray())
    }

    fun addRange(fromIndex: Int, toIndex: Int) {
        mileages.addAll(mileagesByRange(fromIndex, toIndex))
    }

    LazyRow(state = rowState, modifier = Modifier.fillMaxHeight()) {
        items(items = mileages, key = { it.index }) { mileage ->
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                content(mileage)
            }

            if (mileage.index == mileages.last().index) {
                addRange(mileage.index + 1, mileage.index + 1 + batch)
            }
        }
    }
}
