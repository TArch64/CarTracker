package ua.tarch64.composeQuery.mutation

typealias MutationSuccess <I, R> = (result: R, MutationContext<I, R>) -> Unit
