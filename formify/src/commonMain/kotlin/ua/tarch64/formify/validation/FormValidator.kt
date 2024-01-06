package ua.tarch64.formify.validation

abstract class FormValidator<V> {
    abstract fun validate(value: V): FormValidation

    class Compose<V>(private val validators: List<FormValidator<V>>): FormValidator<V>() {
        override fun validate(value: V): FormValidation {
            for (validator in validators) {
                val validation = validator.validate(value)

                if (validation is FormErrorValidation) {
                    return validation
                }
            }

            return FormSuccessValidation()
        }
    }

    class Fun<V>(private val perform: (value: V) -> FormValidation): FormValidator<V>() {
        override fun validate(value: V) = perform(value)
    }

    class NotBlank: FormValidator<CharSequence>() {
        override fun validate(value: CharSequence) = when {
            value.isNotBlank() -> FormSuccessValidation()
            else -> FormErrorValidation.Text("This field can't be blank")
        }
    }
}
