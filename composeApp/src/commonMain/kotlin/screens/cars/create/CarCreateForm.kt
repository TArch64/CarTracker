package screens.cars.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.control.FormObjectControl
import ua.tarch64.formify.ui.Form
import ua.tarch64.formify.ui.FormField
import ua.tarch64.formify.validation.FormValidator

@Composable
fun CarCreateForm(onCreate: (form: FormObjectControl) -> Unit) {
    val formObject = remember {
        FormObjectControl(
            controls = mapOf(
                "name" to FormFieldControl("", FormValidator.NotBlank()),
                "color" to FormFieldControl("#000", FormValidator.NotBlank())
            )
        )
    }

    Form(model = formObject) {
        Column {
            FormField(
                field = formObject.getField<String>("name"),
                error = { Text(it.message) }
            ) {
                OutlinedTextField(
                    value = it.value,
                    onValueChange = it.setValue,
                    label = { Text("Name") },
                    singleLine = true,
                    interactionSource = it.interactionSource,
                    modifier = Modifier.fillMaxWidth(),
                    isError = it.invalid
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormField(
                field = formObject.getField<String>("color"),
                error = { Text(it.message) }
            ) {
                OutlinedTextField(
                    value = it.value,
                    onValueChange = it.setValue,
                    label = { Text("Color") },
                    singleLine = true,
                    interactionSource = it.interactionSource,
                    modifier = Modifier.fillMaxWidth(),
                    isError = it.invalid
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    formObject.validate()
                    if (formObject.isValid) onCreate(formObject)
                }
            ) {
                Text("Add Car")
            }
        }
    }
}
