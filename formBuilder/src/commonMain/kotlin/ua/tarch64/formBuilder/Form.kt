package ua.tarch64.formBuilder

import androidx.compose.runtime.Composable

@Composable
fun Form(content: @Composable () -> Unit) {
    content()
}
