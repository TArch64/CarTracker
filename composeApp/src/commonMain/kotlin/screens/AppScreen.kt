package screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

abstract class AppScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    val navigator @Composable get() = LocalNavigator.currentOrThrow
}
