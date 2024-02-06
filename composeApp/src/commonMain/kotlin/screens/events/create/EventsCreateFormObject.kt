package screens.events.create

import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.control.FormObjectControl
import ua.tarch64.formify.control.GenericFormObject
import ua.tarch64.formify.validation.FormValidator

@GenericFormObject
abstract class EventsCreateFormObject(name: String) : FormObjectControl() {
    val nameControl = FormFieldControl(name, FormValidator.NotBlank())
}
