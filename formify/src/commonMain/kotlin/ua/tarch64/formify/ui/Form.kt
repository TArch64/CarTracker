package ua.tarch64.formify.ui

import androidx.compose.runtime.Composable
import ua.tarch64.formify.model.FormObjectControl

@Composable
fun Form(model: FormObjectControl, content: @Composable () -> Unit) {
    bindLaunchedEffect(model)
    content()
}