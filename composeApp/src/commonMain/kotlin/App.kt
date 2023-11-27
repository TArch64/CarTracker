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
import org.koin.dsl.module
import storage.database.Car
import storage.database.Database
import storage.database.DatabaseFactory

val commonModule = module {
    single { DatabaseFactory(driverFactory = get()) }
    single { get<DatabaseFactory>().createDatabase() }
}

@Composable
fun App(platformModule: Module) {
    KoinApplication(moduleList = { listOf(platformModule, commonModule) }) {
        val database = koinInject<Database>()
        val cars: List<Car> = remember { database.carQueries.all().executeAsList() }

        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Hello count ${cars.size + 1}")
            }
        }
    }
}
