package screens.cars.create

import cafe.adriel.voyager.core.model.ScreenModel
import storage.repository.CarRepository

class CarCreateModel(private val carRepository: CarRepository): ScreenModel {
    fun create() {}
}
