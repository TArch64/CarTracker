package ua.tarch64.formify.model

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

class FormFieldControl<V>(initial: V) : FormControl() {
    private val valueState = mutableStateOf(initial)

    var value: V
        get() = valueState.value

        set(value) {
            valueState.value = value
        }

    private val touchedState = mutableStateOf(false)

    val touched get() = touchedState.value
    val interactionSource = MutableInteractionSource()

    @Composable
    override fun initialize(coroutineScope: CoroutineScope) {
        super.initialize(coroutineScope)

        coroutineScope.launch {
            interactionSource
                .interactions
                .takeWhile { it is FocusInteraction.Unfocus }
                .collect { touchedState.value = true }
        }
    }
}
