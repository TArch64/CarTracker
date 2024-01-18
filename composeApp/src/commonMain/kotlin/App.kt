import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import screens.cars.carsModule
import screens.events.eventsModule
import screens.splash.SplashScreen
import screens.splash.splashScreenModule
import storage.database.databaseModule
import storage.repository.repositoryModule
import ua.tarch64.composeQuery.ComposeQueryProvider

fun buildModules(platformModule: Module): List<Module> = listOf(
    platformModule,
    databaseModule,
    repositoryModule,
    splashScreenModule,
    carsModule,
    eventsModule
)

@Composable
fun App(platformModule: Module) {
    KoinApplication(moduleList = { buildModules(platformModule) }) {
        ComposeQueryProvider {
            MaterialTheme {
                Navigator(SplashScreen()) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }
    }
}
