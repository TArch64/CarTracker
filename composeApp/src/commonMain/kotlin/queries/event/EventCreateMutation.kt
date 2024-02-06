package queries.event

import androidx.compose.runtime.Composable
import migrations.Car
import migrations.Event
import org.koin.compose.koinInject
import storage.repository.EventRepository
import storage.repository.model.Mileage
import ua.tarch64.composeQuery.Mutation
import ua.tarch64.composeQuery.mutation.MutationContent

data class EventCreateMutationInput(
    val name: String,
)

@Composable
fun EventCreateMutation(
    car: Car,
    mileage: Mileage,
    content: MutationContent<EventCreateMutationInput, Event>
) {
    val eventRepository = koinInject<EventRepository>()

    Mutation(
        mutate = {
            eventRepository.create(
                name = it.name,
                mileage = mileage,
                car = car
            )
        },

        onSuccess = { _, context ->
            context.invalidateQuery(EventQueryKeys.byMileage(mileage))
        },

        content = content
    )
}
