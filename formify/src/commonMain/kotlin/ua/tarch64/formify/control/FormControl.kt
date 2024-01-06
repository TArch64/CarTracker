package ua.tarch64.formify.control

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope

abstract class FormControl {
    var isInitialized = false
    abstract val isValid: Boolean
    val isInvalid get() = !isValid

    @Composable
    open fun initialize(coroutineScope: CoroutineScope) {
        isInitialized = true
    }

    abstract fun validate()
}
