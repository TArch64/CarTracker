package queries.event

import androidx.compose.runtime.Composable
import migrations.Event
import org.koin.compose.koinInject
import storage.repository.EventRepository
import storage.repository.model.Mileage
import ua.tarch64.composeQuery.Query
import ua.tarch64.composeQuery.QueryContent

@Composable
fun EventsQuery(mileage: Mileage, content: QueryContent<List<Event>>) {
    val eventRepository = koinInject<EventRepository>()

    Query(
        key = EventQueryKeys.byMileage(mileage),
        query = { eventRepository.byMileageRange(mileage, mileage.next) },
        content = content
    )
}
