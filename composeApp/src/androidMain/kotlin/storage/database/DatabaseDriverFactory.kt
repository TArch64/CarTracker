package storage.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(schema: DatabaseSchema, name: String): SqlDriver {
        return AndroidSqliteDriver(
            name = name,
            schema = schema,
            context = context,
            callback = DatabaseDriverCallback(schema)
        )
    }
}
