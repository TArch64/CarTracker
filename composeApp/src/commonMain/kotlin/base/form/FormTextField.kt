package base.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.ui.FormField

@Composable
fun FormTextField(
    label: String,
    field: FormFieldControl<String>,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    FormField(field, error = { FormError(it.message) }) { context ->
        OutlinedTextField(
            value = context.value,
            onValueChange = context.setValue,
            label = { Text(label) },
            singleLine = singleLine,
            interactionSource = context.interactionSource,
            modifier = Modifier.fillMaxWidth().then(modifier),
            isError = context.invalid,
            keyboardOptions = keyboardOptions
        )
    }
}
