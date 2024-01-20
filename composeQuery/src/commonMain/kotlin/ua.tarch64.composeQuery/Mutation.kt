package ua.tarch64.composeQuery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import ua.tarch64.composeQuery.mutation.MutationContent
import ua.tarch64.composeQuery.mutation.MutationContext
import ua.tarch64.composeQuery.mutation.MutationExecute
import ua.tarch64.composeQuery.mutation.MutationSuccess

@Composable
fun <I, R> Mutation(
    mutate: MutationExecute<I, R>,
    onSuccess: MutationSuccess<I, R> = { _, _ -> },
    content: MutationContent<I, R>
) {
    val coroutineScope = LocalCoroutineScope.currentOrThrow
    val store = LocalQueryStore.currentOrThrow
    var processing by remember { mutableStateOf(false) }
    lateinit var context: MutationContext<I, R>

    context = MutationContext(
        processing = processing,
        onInvalidateQuery = { store.invalidateQuery(it) },
        onInvalidateQueries = { store.invalidateQuery(it) }
    ) {
        coroutineScope.launch {
            try {
                processing = true
                onSuccess(mutate(it), context)
            } finally {
                processing = false
            }
        }
    }

    content(context)
}
