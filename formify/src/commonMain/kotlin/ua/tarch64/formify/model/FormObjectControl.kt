package ua.tarch64.formify.model

class FormObjectControl(private val controls: Map<String, FormControl>) : FormControl() {
    fun <V> getField(name: String): FormFieldControl<V> {
        val field = controls[name] ?: throw Exception("No field with name [$name]")

        if (field !is FormFieldControl<*>) {
            throw Exception("Control with name [$name] is not an instance of FormFieldControl")
        }

        @Suppress("UNCHECKED_CAST")
        return field as FormFieldControl<V>
    }
}
