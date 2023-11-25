import androidx.compose.ui.window.ComposeUIViewController
import storage.database.DatabaseDriverFactory
import storage.database.DatabaseFactory

fun MainViewController() = ComposeUIViewController {
    App(databaseFactory = DatabaseFactory(DatabaseDriverFactory()))
}
