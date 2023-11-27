package storage.database

import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseFactory(driverFactory = get()) }
    single { get<DatabaseFactory>().createDatabase() }
}
