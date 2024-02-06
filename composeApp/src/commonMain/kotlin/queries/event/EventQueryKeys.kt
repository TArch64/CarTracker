package queries.event

import storage.repository.model.Mileage
import ua.tarch64.composeQuery.query.QueryKey

object EventQueryKeys {
    fun byMileage(mileage: Mileage): QueryKey = listOf("Event", "Get", mileage.value)
}
