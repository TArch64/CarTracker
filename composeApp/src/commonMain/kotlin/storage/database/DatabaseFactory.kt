package storage.database

import app.cash.sqldelight.db.SqlDriver
import migrations.Car
import storage.database.columnAdapters.CarMileageColumnAdapter
import storage.database.columnAdapters.ColorColumnAdapter

class DatabaseFactory(private val driverFactory: DatabaseDriverFactory) {
    fun createDatabase() = Database(
        driver = createDriver(),
        CarAdapter = createCarColumnAdapter()
    )

    private fun createDriver(): SqlDriver {
        return driverFactory.createDriver(Database.Schema, "app.db")
    }

    private fun createCarColumnAdapter() = Car.Adapter(
        colorAdapter = ColorColumnAdapter(),
        mileageAdapter = CarMileageColumnAdapter()
    )
}
