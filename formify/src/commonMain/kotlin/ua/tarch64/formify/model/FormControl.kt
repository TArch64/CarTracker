package ua.tarch64.formify.model

import com.benasher44.uuid.Uuid
import kotlinx.coroutines.CoroutineScope

abstract class FormControl(val uid: Uuid) {
    abstract fun launchedEffect(coroutineScope: CoroutineScope)
}
