package storage.database.columnAdapters

import app.cash.sqldelight.ColumnAdapter
import storage.repository.model.CarMileage

class CarMileageColumnAdapter: ColumnAdapter<CarMileage, Long> {
    override fun decode(databaseValue: Long) = CarMileage(databaseValue.toInt())
    override fun encode(value: CarMileage): Long = value.value.toLong()
}
