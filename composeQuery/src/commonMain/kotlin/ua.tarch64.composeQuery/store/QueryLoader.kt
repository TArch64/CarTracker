package ua.tarch64.composeQuery.store

typealias QueryLoader<D> = suspend () -> D
