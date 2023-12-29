package ua.tarch64.formify.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import ua.tarch64.formify.model.FormControl

@Composable
internal fun bindLaunchedEffect(control: FormControl) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(control.uid) { control.launchedEffect(coroutineScope) }
}
