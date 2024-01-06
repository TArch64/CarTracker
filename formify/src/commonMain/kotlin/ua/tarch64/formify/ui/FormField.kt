package ua.tarch64.formify.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.validation.FormErrorValidation
import ua.tarch64.formify.validation.FormValidation

data class FormFieldContext<V>(
    val value: V,
    val setValue: (value: V) -> Unit,
    val touched: Boolean,
    val interactionSource: MutableInteractionSource,
    val invalid: Boolean
)

@Composable
private fun FormFieldValidation(
    validation: FormValidation,
    content: @Composable (error: FormErrorValidation) -> Unit
) {
    if (validation is FormErrorValidation) content(validation)
}

@Composable
fun <V> FormField(
    field: FormFieldControl<V>,
    error: (@Composable (error: FormErrorValidation) -> Unit)? = null,
    content: @Composable (context: FormFieldContext<V>) -> Unit
) {
    initializeControl(field)

    content(
        FormFieldContext(
            value = field.value,
            setValue = { field.value = it },
            touched = field.touched,
            interactionSource = field.interactionSource,
            invalid = field.isInvalid
        )
    )

    if (error !== null) {
        FormFieldValidation(validation = field.validation, content = error)
    }
}
