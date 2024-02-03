package screens.cars.create

import androidx.compose.ui.graphics.Color
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.control.FormObjectControl
import ua.tarch64.formify.control.GenericFormObject
import ua.tarch64.formify.validation.FormValidator

@GenericFormObject
abstract class CarCreateFormObject(name: String, color: Color, mileage: Int) : FormObjectControl() {
    val nameControl = FormFieldControl(name, FormValidator.NotBlank())
    val colorControl = FormFieldControl(color)
    val mileageControl = FormFieldControl(mileage)
}
