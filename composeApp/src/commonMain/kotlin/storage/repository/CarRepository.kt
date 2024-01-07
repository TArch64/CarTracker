package storage.repository

import androidx.compose.ui.graphics.Color
import storage.database.Car
import storage.database.Database

class CarRepository(database: Database): Repository(database) {
    private val query get() = database.carQueries

    fun first(): Car? = query.first().executeAsOneOrNull()

    fun create(name: String, color: Color): Car {
        query.create(name = name, color = color)

        return Car(
            id = query.lastCreatedId().executeAsOne(),
            name = name,
            color = color
        )
    }
}
