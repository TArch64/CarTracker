package ua.tarch64.formify.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import ua.tarch64.formify.model.FormControl

@Composable
internal fun initializeControl(control: FormControl) {
    val coroutineScope = rememberCoroutineScope()
    if (!control.isInitialized) control.initialize(coroutineScope)
}
