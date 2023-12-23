package screens.cars.create

import ua.tarch64.formBuilder.annotation.FormObject

@FormObject
interface CarCreateObject {
    val name: String
    val color: String
}
