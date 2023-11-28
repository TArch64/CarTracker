package screens.cars.create

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

class CarCreateScreen: Screen {
    override val key: ScreenKey get() = uniqueScreenKey

    @Composable
    override fun Content() {
        Text("No car created yet")
    }
}
