package ua.tarch64.formBuilder

import androidx.compose.runtime.Composable

@Composable
fun <Model> Form(model: Model?, content: @Composable () -> Unit) {
    content()
}
