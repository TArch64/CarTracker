package storage.database

import migrations.Car
import migrations.Event
import storage.database.columnAdapters.ColorColumnAdapter
import storage.database.columnAdapters.MileageColumnAdapter

class DatabaseFactory(private val driverFactory: DatabaseDriverFactory) {
    private val colorColumnAdapter = ColorColumnAdapter()
    private val mileageColumnAdapter = MileageColumnAdapter()

    fun createDatabase() = Database(
        driver = driverFactory.createDriver(Database.Schema, "app.db"),

        CarAdapter = Car.Adapter(
            colorAdapter = colorColumnAdapter,
            mileageAdapter = mileageColumnAdapter
        ),

        EventAdapter = Event.Adapter(
            mileageAdapter = mileageColumnAdapter
        )
    )
}
