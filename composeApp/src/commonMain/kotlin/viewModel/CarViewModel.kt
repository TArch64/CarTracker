package viewModel

import storage.database.Car
import storage.repository.CarRepository

class CarViewModel(private val repository: CarRepository): ViewModel<Car?>() {
    override fun createState(): Car? = fetchCar()

    fun refresh() {
        setState(fetchCar())
    }

    private fun fetchCar(): Car? {
        return repository.first()
    }
}
