package ua.tarch64.formify.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import ua.tarch64.formify.model.FormFieldControl

data class FormFieldContext<V>(
    val value: V,
    val setValue: (value: V) -> Unit,
    val interactionSource: MutableInteractionSource
)

@Composable
fun <V> FormField(
    field: FormFieldControl<V>,
    content: @Composable (context: FormFieldContext<V>) -> Unit
) {
    bindLaunchedEffect(field)

    content(
        FormFieldContext(
            value = field.value.value,
            setValue = { field.value.value = it },
            interactionSource = field.interactionSource
        )
    )
}
