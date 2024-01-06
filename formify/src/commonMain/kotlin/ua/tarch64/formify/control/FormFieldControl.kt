package ua.tarch64.formify.control

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import ua.tarch64.formify.validation.FormSuccessValidation
import ua.tarch64.formify.validation.FormValidation
import ua.tarch64.formify.validation.FormValidator

class FormFieldControl<V>(
    initial: V,
    private val validator: FormValidator<V>? = null
) : FormControl() {
    private val valueState = mutableStateOf(initial)

    var value: V
        get() = valueState.value

        set(value) {
            valueState.value = value
        }

    private val touchedState = mutableStateOf(false)

    val touched get() = touchedState.value
    val interactionSource = MutableInteractionSource()

    private val validationState = mutableStateOf<FormValidation>(FormSuccessValidation())
    val validation get() = validationState.value
    override val isValid: Boolean get() = validation is FormSuccessValidation

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

    override fun validate() {
        if (validator != null) validationState.value = validator.validate(value)
    }
}
