package storage.repository

import migrations.Car
import migrations.Event
import storage.database.Database
import storage.repository.base.Repository
import storage.repository.model.Mileage

class EventRepository(database: Database) : Repository(database) {
    private val query get() = database.eventQueries

    fun byMileageRange(from: Mileage, to: Mileage) = query.byMileageRange(from, to).executeAsList()

    fun create(name: String, car: Car, mileage: Mileage): Event {
        query.create(name = name, carId = car.id, mileage = mileage)

        return Event(
            id = query.lastCreatedId().executeAsOne(),
            name = name,
            mileage = mileage,
            carId = car.id
        )
    }
}
