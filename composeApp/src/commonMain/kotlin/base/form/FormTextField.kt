package base.form

import androidx.compose.foundation.layout.fillMaxWidth
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
    modifier: Modifier = Modifier
) {
    FormField(field, error = { FormError(it.message) }) {
        OutlinedTextField(
            value = it.value,
            onValueChange = it.setValue,
            label = { Text(label) },
            singleLine = singleLine,
            interactionSource = it.interactionSource,
            modifier = Modifier.fillMaxWidth().then(modifier),
            isError = it.invalid
        )
    }
}
