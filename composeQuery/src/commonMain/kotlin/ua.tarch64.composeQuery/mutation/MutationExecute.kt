package ua.tarch64.composeQuery.mutation

typealias MutationExecute<I, R> = suspend (input: I) -> R
