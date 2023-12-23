package ua.tarch64.formBuilder

import androidx.compose.runtime.Composable
import ua.tarch64.formBuilder.model.FormObjectModel

@Composable
fun <Model : FormObjectModel> Form(model: Model?, content: @Composable () -> Unit) {
    content()
}
