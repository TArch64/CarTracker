package screens.events.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.koin.getScreenModel
import org.koin.core.parameter.parametersOf
import screens.AppScreen
import storage.database.Car

class EventsListScreen(private val car: Car): AppScreen() {
    @Composable
    override fun Content() {
        val model = getScreenModel<EventsListModel> { parametersOf(car) }
    }
}
