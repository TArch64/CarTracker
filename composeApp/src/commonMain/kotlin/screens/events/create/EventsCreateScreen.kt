package screens.events.create

import androidx.compose.runtime.Composable
import queries.car.RequiredCarQuery
import queries.event.EventCreateMutation
import queries.event.EventCreateMutationInput
import screens.ModalScreen
import storage.repository.model.Mileage

class EventsCreateScreen(private val mileage: Mileage) : ModalScreen() {
    override val headerTitle = "New Event"

    @Composable
    override fun Body() {
        val navigator = getNavigator()

        RequiredCarQuery { car ->
            EventCreateMutation(car, mileage) { mutation ->
                EventsCreateForm { form ->
                    mutation.mutate(
                        input = EventCreateMutationInput(
                            name = form.name
                        )
                    )
                    navigator.pop()
                }
            }
        }
    }
}
