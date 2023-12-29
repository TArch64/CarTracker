package ua.tarch64.formify.model

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope

class FormObjectControl(uid: Uuid, private val controls: Map<String, FormControl>) : FormControl(uid) {
    constructor(controls: Map<String, FormControl>) : this(uuid4(), controls)

    override fun launchedEffect(coroutineScope: CoroutineScope) {}

    fun <V> getField(name: String): FormFieldControl<V> {
        val field = controls[name] ?: throw Exception("No field with name [$name]")

        if (field !is FormFieldControl<*>) {
            throw Exception("Control with name [$name] is not an instance of FormFieldControl")
        }

        @Suppress("UNCHECKED_CAST")
        return field as FormFieldControl<V>
    }
}
