package ua.tarch64.formify.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ua.tarch64.formify.control.FormObjectControl

@Composable
fun <C : FormObjectControl> Form(form: C, content: @Composable FormHandler.() -> Unit) {
    initializeControl(form)

    val submitHandler = remember {
        object : FormHandler {
            override fun handleSubmit(onValid: () -> Unit): () -> Unit = {
                form.validate()
                if (form.isValid) onValid()
            }
        }
    }

    content.invoke(submitHandler)
}
