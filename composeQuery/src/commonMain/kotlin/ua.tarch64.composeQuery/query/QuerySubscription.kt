package ua.tarch64.composeQuery.query

import com.benasher44.uuid.Uuid
import kotlinx.coroutines.flow.StateFlow

data class QuerySubscription<D>(
    val id: Uuid,
    val stateFlow: StateFlow<QueryResult<D>>,
    internal val onUnsubscribe: (subscription: QuerySubscription<D>) -> Unit
) {
    lateinit var source: QuerySource<D>

    fun unsubscribe() {
        onUnsubscribe(this)
    }
}
