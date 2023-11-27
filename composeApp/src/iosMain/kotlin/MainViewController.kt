import androidx.compose.ui.window.ComposeUIViewController
import org.koin.dsl.module
import storage.database.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    App(platformModule = module {
        single { DatabaseDriverFactory() }
    })
}
