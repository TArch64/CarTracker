package screens.cars.create

import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import storage.repository.CarRepository
import ua.tarch64.formify.control.FormObjectControl

class CarCreateModel(private val carRepository: CarRepository): ScreenModel {
    fun create(formObject: FormObjectControl) {
        val created = carRepository.create(
            name = formObject.getField<String>("name").value,
            color = formObject.getField<Color>("color").value
        )
        println()
    }
}
