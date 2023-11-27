import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.module.Module
import storage.database.Car
import storage.database.databaseModule
import storage.repository.CarRepository
import storage.repository.repositoryModule

fun buildModules(platformModule: Module): List<Module> = listOf(
    platformModule,
    databaseModule,
    repositoryModule
)

@Composable
fun App(platformModule: Module) {
    KoinApplication(moduleList = { buildModules(platformModule) }) {
        val carRepository: CarRepository = koinInject()
        val cars: List<Car> = remember { carRepository.all() }

        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Hello count ${cars.size}")
            }
        }
    }
}
