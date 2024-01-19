package ua.tarch64.composeQuery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import ua.tarch64.composeQuery.store.QueryKey
import ua.tarch64.composeQuery.store.QueryLoader
import ua.tarch64.composeQuery.store.QueryResult

typealias QueryLoadingContent = @Composable () -> Unit
typealias QueryErrorContent = @Composable (exception: Throwable) -> Unit
typealias QueryContent<D> = @Composable (data: D) -> Unit

@Composable
fun <D> Query(
    key: QueryKey,
    query: QueryLoader<D>,
    loading: QueryLoadingContent = {},
    error: QueryErrorContent = {},
    content: QueryContent<D>
) {
    val store = LocalComposeQueryStore.current
    val subscription = remember { store.watchQuery(key, query) }
    val state by subscription.stateFlow.collectAsState()

    DisposableEffect(subscription.id) {
        onDispose(subscription::unsubscribe)
    }

    when (state) {
        is QueryResult.Loading<*> -> loading()
        is QueryResult.Error<*> -> error((state as QueryResult.Error<D>).exception)
        is QueryResult.Loaded<*> -> content((state as QueryResult.Loaded<D>).data)
    }
}
