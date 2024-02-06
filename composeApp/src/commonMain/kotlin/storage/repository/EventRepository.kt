package storage.repository

import storage.database.Database
import storage.repository.base.Repository
import storage.repository.model.Mileage

class EventRepository(database: Database) : Repository(database) {
    private val query get() = database.eventQueries

    fun byMileageRange(from: Mileage, to: Mileage) = query.byMileageRange(from, to).executeAsList()
}
