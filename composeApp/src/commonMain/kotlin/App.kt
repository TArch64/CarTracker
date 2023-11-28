import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.module.Module
import storage.database.databaseModule
import storage.repository.repositoryModule
import viewModel.CarViewModel
import viewModel.viewModelModule

fun buildModules(platformModule: Module): List<Module> = listOf(
    platformModule,
    databaseModule,
    repositoryModule,
    viewModelModule
)

@Composable
fun App(platformModule: Module) {
    KoinApplication(moduleList = { buildModules(platformModule) }) {
        val carViewModel: CarViewModel = koinInject()

        MaterialTheme {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if (carViewModel.state == null) {
                    Text("No car created yet")
                } else {
                    Text("Car is created")
                }
            }
        }
    }
}
