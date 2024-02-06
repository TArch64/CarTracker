package storage.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlSchema

typealias DatabaseSchema = SqlSchema<QueryResult.Value<Unit>>
