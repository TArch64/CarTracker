import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import screens.cars.carsModule
import screens.splash.SplashScreen
import screens.splash.splashScreenModule
import storage.database.databaseModule
import storage.repository.repositoryModule

fun buildModules(platformModule: Module): List<Module> = listOf(
    platformModule,
    databaseModule,
    repositoryModule,
    splashScreenModule,
    carsModule
)

@Composable
fun App(platformModule: Module) {
    KoinApplication(moduleList = { buildModules(platformModule) }) {
        MaterialTheme {
            Navigator(SplashScreen()) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}
