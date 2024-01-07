package screens.splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import screens.AppScreen
import screens.cars.create.CarCreateScreen
import screens.events.list.EventsListScreen

class SplashScreen : AppScreen() {
    @Composable
    override fun Content() {
        val model = getScreenModel<SplashScreenModel>()
        val car = model.getCar()

        if (car == null) {
            navigator.replace(CarCreateScreen())
        } else {
            navigator.replace(EventsListScreen(car))
        }
    }
}
