package screens.splash

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import screens.AppScreen
import screens.cars.create.CarCreateScreen

class SplashScreen : AppScreen() {
    @Composable
    override fun Content() {
        val model = getScreenModel<SplashScreenModel>()

        if (model.hasCar) {
            Text("Car is created")
        } else {
            navigator.replace(CarCreateScreen())
        }
    }
}