package screens.cars.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import base.Constants
import base.form.FormColorSwatches
import base.form.FormTextField
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.control.FormObjectControl
import ua.tarch64.formify.ui.Form
import ua.tarch64.formify.validation.FormValidator

@Composable
fun CarCreateForm(onCreate: (form: FormObjectControl) -> Unit) {
    val formObject = remember {
        FormObjectControl(
            controls = mapOf(
                "name" to FormFieldControl("", FormValidator.NotBlank()),
                "color" to FormFieldControl(Constants.Car.COLORS.first())
            )
        )
    }

    Form(model = formObject) {
        Column {
            FormTextField(
                label = "Name",
                field = formObject.getField("name"),
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormColorSwatches(
                label = "Color",
                field = formObject.getField("color"),
                options = Constants.Car.COLORS
            )

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
