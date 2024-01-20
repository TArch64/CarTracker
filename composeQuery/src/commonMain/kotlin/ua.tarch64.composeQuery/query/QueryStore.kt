package ua.tarch64.composeQuery.query

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class QueryStore(
    private val queries: MutableMap<QueryKey, QuerySource<*>>,
    private val coroutineScope: CoroutineScope
) {
    fun <D> watchQuery(key: QueryKey, loader: QueryLoader<D>): QuerySubscription<D> {
        val source = initQuerySource(key, loader)

        return QuerySubscription(uuid4(), source.stateFlow, ::unsubscribe).also {
            source.addSubscription(it)
        }
    }

    private fun <D> initQuerySource(key: QueryKey, loader: QueryLoader<D>): QuerySource<D> {
        if (queries.contains(key)) {
            @Suppress("UNCHECKED_CAST")
            return queries[key] as QuerySource<D>
        }

        return QuerySource(key, loader).also {
            queries[key] = it
            coroutineScope.launch { it.loadQuery() }
        }
    }

    private fun <D> unsubscribe(subscription: QuerySubscription<D>) {
        val source = subscription.source

        source.removeSubscription(subscription)

        if (!source.hasSubscriptions) {
            queries.remove(source.key)
        }
    }

    fun invalidateQuery(key: QueryKey) {
        val source = queries[key]

        if (source != null) {
            coroutineScope.launch {
                source.loadQuery()
            }
        }
    }

    fun invalidateQuery(keys: Set<QueryKey>) {
        keys.forEach(::invalidateQuery)
    }
}
