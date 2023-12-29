package ua.tarch64.formify.model

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.mutableStateOf
import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch

class FormFieldControl<V>(uid: Uuid, initial: V) : FormControl(uid) {
    constructor(initial: V) : this(uuid4(), initial)

    val value = mutableStateOf(initial)
    val touched = mutableStateOf(false)
    val interactionSource = MutableInteractionSource()

    override fun launchedEffect(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            interactionSource
                .interactions
                .takeWhile { it is FocusInteraction.Unfocus }
                .collect { touched.value = true }
        }
    }
}
