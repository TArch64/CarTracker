package ua.tarch64.composeQuery.mutation

import ua.tarch64.composeQuery.query.QueryKey

data class MutationContext<I, R>(
    val processing: Boolean,
    private val onInvalidateQuery: (key: QueryKey) -> Unit,
    private val onInvalidateQueries: (key: Set<QueryKey>) -> Unit,
    private val onMutate: (input: I) -> Unit,
) {
    fun mutate(input: I) = onMutate(input)
    fun invalidateQuery(key: QueryKey) = onInvalidateQuery(key)
    fun invalidateQuery(keys: Set<QueryKey>) = onInvalidateQueries(keys)
}
