package storage.repository

import androidx.compose.ui.graphics.Color
import migrations.Car
import storage.database.Database
import storage.repository.model.CarMileage

class CarRepository(database: Database): Repository(database) {
    private val query get() = database.carQueries

    fun first(): Car? = query.first().executeAsOneOrNull()

    fun create(name: String, color: Color, mileage: CarMileage): Car {
        query.create(name = name, color = color, mileage = mileage)

        return Car(
            id = query.lastCreatedId().executeAsOne(),
            name = name,
            color = color,
            mileage = mileage
        )
    }
}
