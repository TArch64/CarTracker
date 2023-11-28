package viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

abstract class ViewModel<D> {
    private val stateFlow: MutableStateFlow<D> by lazy { MutableStateFlow(createState()) }
    val stateChanges: StateFlow<D> get() = stateFlow.asStateFlow()

    val state @Composable get() = stateChanges.collectAsState().value
    fun setState(value: D) = runBlocking { stateFlow.emit(value) }

    protected abstract fun createState(): D
}
