package screens.cars.create

import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import storage.repository.CarRepository
import storage.repository.model.CarMileage
import ua.tarch64.formify.control.FormObjectControl

class CarCreateModel(private val carRepository: CarRepository): ScreenModel {
    fun create(formObject: FormObjectControl) {
        carRepository.create(
            name = formObject.getField<String>("name").value,
            color = formObject.getField<Color>("color").value,
            mileage = CarMileage(formObject.getField<Int>("mileage").value)
        )
    }
}
