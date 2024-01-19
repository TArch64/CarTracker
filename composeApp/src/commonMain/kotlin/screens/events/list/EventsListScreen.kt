package screens.events.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import migrations.Car
import queries.car.RequiredCarQuery
import screens.AppScreen
import screens.events.list.part.EventsListPart

class EventsListScreen(private val car: Car): AppScreen() {
    @Composable
    override fun Content() {
        RequiredCarQuery {
            Column(modifier = Modifier.fillMaxHeight()) {
                Spacer(modifier = Modifier.weight(1f))

                EventsScroller(initialMileage = car.mileage, batch = 20) { mileage ->
                    EventsListPart(car = car, mileage = mileage)
                }
            }
        }
    }
}
