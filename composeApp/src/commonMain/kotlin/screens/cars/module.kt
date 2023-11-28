package screens.cars

import org.koin.dsl.module
import screens.cars.create.carCreateModule

val carsModule = module {
    includes(carCreateModule)
}
