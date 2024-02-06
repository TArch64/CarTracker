package storage.database

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(schema: DatabaseSchema, name: String): SqlDriver
}
