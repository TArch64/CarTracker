package ua.tarch64.formify.validation

interface FormErrorValidation: FormValidation {
    val message: String

    class Text(override val message: String): FormErrorValidation
}
