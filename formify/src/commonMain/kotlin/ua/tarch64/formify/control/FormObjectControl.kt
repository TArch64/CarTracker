package ua.tarch64.formify.control

class FormObjectControl(private val controls: Map<String, FormControl>) : FormControl() {
    override val isValid: Boolean get() = controls.all { it.value.isValid }

    fun <V> getField(name: String): FormFieldControl<V> {
        val field = controls[name] ?: throw Exception("No field with name [$name]")

        if (field !is FormFieldControl<*>) {
            throw Exception("Control with name [$name] is not an instance of FormFieldControl")
        }

        @Suppress("UNCHECKED_CAST")
        return field as FormFieldControl<V>
    }

    override fun validate() {
        for (control in controls.values) {
            control.validate()
        }
    }
}
