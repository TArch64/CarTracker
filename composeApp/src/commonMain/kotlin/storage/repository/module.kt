package storage.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { CarRepository(get()) }
}
