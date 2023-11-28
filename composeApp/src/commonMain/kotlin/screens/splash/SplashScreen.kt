package screens.splash

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import screens.cars.create.CarCreateScreen

class SplashScreen: Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val model = getScreenModel<SplashScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        if (model.hasCar) {
            Text("Car is created")
        } else {
            navigator.push(CarCreateScreen())
        }
    }
}