package ua.tarch64.formify.ui

import androidx.compose.runtime.Composable
import ua.tarch64.formify.control.FormObjectControl

@Composable
fun <C: FormObjectControl> Form(model: C, content: @Composable () -> Unit) {
    initializeControl(model)
    content()
}
