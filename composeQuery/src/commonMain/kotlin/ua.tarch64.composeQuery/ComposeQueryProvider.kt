package ua.tarch64.composeQuery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import ua.tarch64.composeQuery.query.QueryStore

val <T> ProvidableCompositionLocal<T?>.currentOrThrow: T @Composable get() {
    return current ?: error("Please wrap your app in ComposeQueryProvider")
}

val LocalCoroutineScope = staticCompositionLocalOf<CoroutineScope?> { null }
val LocalQueryStore = staticCompositionLocalOf<QueryStore?> { null }

@Composable
fun ComposeQueryProvider(content: @Composable () -> Unit) {
    @OptIn(DelicateCoroutinesApi::class)
    val coroutineScope = CoroutineScope(GlobalScope.coroutineContext)

    val providers = remember {
        arrayOf(
            LocalCoroutineScope provides coroutineScope,

            LocalQueryStore provides QueryStore(
                queries = mutableMapOf(),
                coroutineScope = coroutineScope
            )
        )
    }

    CompositionLocalProvider(*providers, content = content)
}
