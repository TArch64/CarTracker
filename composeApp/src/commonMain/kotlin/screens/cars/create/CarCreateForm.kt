package screens.cars.create

import ua.tarch64.formBuilder.annotation.FormModel

@FormModel
interface CarCreateForm {
    val name: String
    val color: String
}
