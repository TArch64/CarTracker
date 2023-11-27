package storage.repository

import storage.database.Database

abstract class Repository(protected val database: Database)
