package ua.tarch64.composeQuery.store

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class QuerySource<D>(
    val key: QueryKey,
    private val loader: QueryLoader<D>,
    private val mutableStateFlow: MutableStateFlow<QueryResult<D>> = MutableStateFlow(QueryResult.Loading()),
    private val subscriptions: MutableList<QuerySubscription<D>> = mutableListOf()
) {
    val stateFlow: StateFlow<QueryResult<D>> get() = mutableStateFlow
    val hasSubscriptions get() = subscriptions.isNotEmpty()

    fun addSubscription(subscription: QuerySubscription<D>) {
        subscriptions.add(subscription)
        subscription.source = this
    }

    fun removeSubscription(subscription: QuerySubscription<D>) {
        subscriptions.remove(subscription)
    }

    suspend fun loadQuery() {
        try {
            mutableStateFlow.tryEmit(QueryResult.Loading())
            mutableStateFlow.tryEmit(QueryResult.Loaded(loader()))
        } catch (error: Throwable) {
            mutableStateFlow.tryEmit(QueryResult.Error(error))
        }
    }
}
