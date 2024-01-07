package screens.splash

import cafe.adriel.voyager.core.model.ScreenModel
import storage.repository.CarRepository

class SplashScreenModel(
    private val carRepository: CarRepository
): ScreenModel {
    fun getCar() = carRepository.first()
}
