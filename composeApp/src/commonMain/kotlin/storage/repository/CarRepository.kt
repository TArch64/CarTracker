package storage.repository

import storage.database.Car
import storage.database.Database

class CarRepository(database: Database): Repository(database) {
    private val query get() = database.carQueries

    fun all(): List<Car> {
        return query.all().executeAsList()
    }
}