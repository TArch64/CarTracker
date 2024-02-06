package screens.events.create

import androidx.compose.runtime.Composable
import queries.car.RequiredCarQuery
import screens.ModalScreen

class EventsCreateScreen : ModalScreen() {
    override val headerTitle = "New Event"

    @Composable
    override fun Body() {
        RequiredCarQuery { car ->

        }
    }
}
