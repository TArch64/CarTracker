package ua.tarch64.composeQuery.mutation

import androidx.compose.runtime.Composable

typealias MutationContent <I, R> = @Composable (context: MutationContext<I, R>) -> Unit
