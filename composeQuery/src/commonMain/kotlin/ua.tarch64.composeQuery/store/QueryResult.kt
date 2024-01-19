package ua.tarch64.composeQuery.store

interface QueryResult<D> {
    class Loading<D>: QueryResult<D>

    data class Error<D>(
        val exception: Throwable
    ): QueryResult<D>

    data class Loaded<D>(
        val data: D
    ): QueryResult<D>
}
