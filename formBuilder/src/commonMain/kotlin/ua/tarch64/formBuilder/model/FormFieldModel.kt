package ua.tarch64.formBuilder.model

import androidx.compose.runtime.mutableStateOf

class FormFieldModel<V>(initial: V) {
    private val state = mutableStateOf(initial)

    var value: V
        get() = state.value
        set(value) { state.value = value }
}
