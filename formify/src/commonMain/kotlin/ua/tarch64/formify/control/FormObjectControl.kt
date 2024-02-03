package ua.tarch64.formify.control

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KProperty0

abstract class FormObjectControl : FormControl() {
    override val isValid: Boolean get() = controlRefs.all { it.get().isValid }

    private lateinit var controlRefs: List<KProperty0<FormControl>>
    protected abstract fun buildControlRefs(): List<KProperty0<FormControl>>

    @Composable
    override fun initialize(coroutineScope: CoroutineScope) {
        super.initialize(coroutineScope)
        controlRefs = buildControlRefs()
    }

    override fun validate() {
        for (control in controlRefs) {
            control.get().validate()
        }
    }
}
