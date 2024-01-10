package base.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.ui.FormField

@Composable
fun FormNumberField(
    label: String,
    field: FormFieldControl<Int>,
    modifier: Modifier = Modifier
) {
    FormField(field, error = { FormError(it.message) }) { context ->
        OutlinedTextField(
            value = context.value.toString(),
            onValueChange = { context.setValue(it.toIntOrNull() ?: 0) },
            label = { Text(label) },
            singleLine = false,
            interactionSource = context.interactionSource,
            modifier = Modifier.fillMaxWidth().then(modifier),
            isError = context.invalid,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }
}
