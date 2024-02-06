package queries.car

import androidx.compose.runtime.Composable
import migrations.Car
import org.koin.compose.koinInject
import storage.repository.CarRepository
import ua.tarch64.composeQuery.Query
import ua.tarch64.composeQuery.QueryContent

@Composable
fun CarQuery(content: QueryContent<Car?>) {
    val carRepository = koinInject<CarRepository>()

    Query(
        key = CarQueryKeys.car,
        query = { carRepository.first() },
        content = content
    )
}

@Composable
fun RequiredCarQuery(content: QueryContent<Car>) {
    CarQuery { if (it != null) content(it) }
}
