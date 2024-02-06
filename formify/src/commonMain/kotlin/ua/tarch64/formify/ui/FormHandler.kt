package ua.tarch64.formify.ui

interface FormHandler {
    fun handleSubmit(onValid: () -> Unit): () -> Unit
}
