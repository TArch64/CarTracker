package screens.cars.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import base.Constants
import base.form.FormColorSwatches
import base.form.FormNumberField
import base.form.FormTextField
import ua.tarch64.formify.ui.Form

@Composable
fun CarCreateForm(onCreate: (form: CarCreateFormObjectValue) -> Unit) {
    val formObject = rememberCarCreateFormObject(
        name = "",
        color = Constants.Car.COLORS.first(),
        mileage = 0
    )

    Form(model = formObject) {
        Column {
            FormTextField(
                label = "Name",
                field = formObject.nameControl,
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormColorSwatches(
                label = "Color",
                field = formObject.colorControl,
                options = Constants.Car.COLORS
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormNumberField(
                label = "Mileage",
                field = formObject.mileageControl
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    formObject.validate()
                    if (formObject.isValid) onCreate(formObject.value)
                }
            ) {
                Text("Add Car")
            }
        }
    }
}
