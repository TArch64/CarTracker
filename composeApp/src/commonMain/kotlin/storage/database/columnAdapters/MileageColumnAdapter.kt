package storage.database.columnAdapters

import app.cash.sqldelight.ColumnAdapter
import storage.repository.model.Mileage

class MileageColumnAdapter : ColumnAdapter<Mileage, Long> {
    override fun decode(databaseValue: Long) = Mileage(databaseValue.toInt())
    override fun encode(value: Mileage): Long = value.value.toLong()
}
