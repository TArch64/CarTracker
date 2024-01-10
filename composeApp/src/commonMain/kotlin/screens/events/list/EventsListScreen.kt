package screens.events.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import migrations.Car
import org.koin.core.parameter.parametersOf
import screens.AppScreen

class EventsListScreen(private val car: Car): AppScreen() {
    @Composable
    override fun Content() {
        val model = getScreenModel<EventsListModel> { parametersOf(car) }

        Column(modifier = Modifier.fillMaxHeight()) {
            Spacer(modifier = Modifier.weight(1f))

            EventsScroller(initialMileage = model.car.mileage, batch = 20) { mileage ->
                Box(modifier = Modifier.width(100.dp).height(100.dp).background(Color.Red)) {
                    Text(mileage.value.toString(), color = Color.White)
                }
            }
        }
    }
}
