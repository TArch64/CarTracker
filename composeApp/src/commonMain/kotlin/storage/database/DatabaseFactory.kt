package storage.database

class DatabaseFactory(private val driverFactory: DatabaseDriverFactory) {
    fun createDatabase(): Database {
        val driver = driverFactory.createDriver(Database.Schema, "app.db")
        return Database(driver)
    }
}
