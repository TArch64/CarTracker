package storage.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration

actual class DatabaseDriverFactory {
    actual fun createDriver(schema: DatabaseSchema, name: String): SqlDriver {
        return NativeSqliteDriver(
            schema = schema,
            name = name,
            onConfiguration = ::onConfiguration
        )
    }

    private fun onConfiguration(config: DatabaseConfiguration): DatabaseConfiguration {
        return config.copy(
            extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
        )
    }
}
