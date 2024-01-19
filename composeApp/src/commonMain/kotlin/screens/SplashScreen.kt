package screens

import androidx.compose.runtime.Composable
import queries.car.CarQuery
import screens.cars.create.CarCreateScreen
import screens.events.list.EventsListScreen

class SplashScreen : AppScreen() {
    @Composable
    override fun Content() {
        CarQuery { car ->
            if (car == null) {
                navigator.replace(CarCreateScreen())
            } else {
                navigator.replace(EventsListScreen(car))
            }
        }
    }
}
