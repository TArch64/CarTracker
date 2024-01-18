package ua.tarch64.composeQuery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import ua.tarch64.composeQuery.store.QueryStore

val LocalComposeQueryStore = staticCompositionLocalOf { QueryStore() }

@Composable
fun ComposeQueryProvider(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalComposeQueryStore provides QueryStore()) {
        content()
    }
}
