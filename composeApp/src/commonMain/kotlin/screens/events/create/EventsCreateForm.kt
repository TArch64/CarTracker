package screens.events.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import base.form.FormTextField
import ua.tarch64.formify.ui.Form

@Composable
fun EventsCreateForm(onCreate: (form: EventsCreateFormObjectValue) -> Unit) {
    val formObject = rememberEventsCreateFormObject(name = "")

    Form(form = formObject) {
        Column(modifier = Modifier.fillMaxSize()) {
            FormTextField(
                label = "Name",
                field = formObject.nameControl
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = handleSubmit { onCreate(formObject.value) }
            ) {
                Text("Add Car")
            }
        }
    }
}
