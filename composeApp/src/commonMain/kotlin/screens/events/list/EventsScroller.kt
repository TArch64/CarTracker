package screens.events.list

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import storage.repository.model.CarMileage
import kotlin.math.max

@Composable
fun EventsScroller(
    initialMileage: CarMileage,
    initialIndexShift: Int = 1,
    batch: Int,
    content: @Composable (mileage: CarMileage) -> Unit
) {
    val rowState = rememberLazyListState(max(initialMileage.index - initialIndexShift, 0))

    fun mileagesByRange(fromIndex: Int, toIndex: Int): List<CarMileage> {
        return (fromIndex..toIndex).map(CarMileage.Companion::byIndex)
    }

    val mileages = remember {
        val mileages = mileagesByRange(0, initialMileage.index + batch)
        mutableStateListOf(*mileages.toTypedArray())
    }

    fun addRange(fromIndex: Int, toIndex: Int) {
        mileages.addAll(mileagesByRange(fromIndex, toIndex))
    }

    LazyRow(state = rowState) {
        items(items = mileages, key = { it.index }) { mileage ->
            content(mileage)

            if (mileage.index == mileages.last().index) {
                addRange(mileage.index + 1, mileage.index + 1 + batch)
            }
        }
    }
}
