package ua.tarch64.formify.model

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

abstract class FormControl {
    var isInitialized = false

    @Composable
    open fun initialize(coroutineScope: CoroutineScope) {
        isInitialized = true
    }
}
