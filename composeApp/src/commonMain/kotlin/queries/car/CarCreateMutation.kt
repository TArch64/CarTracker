package queries.car

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import migrations.Car
import org.koin.compose.koinInject
import storage.repository.CarRepository
import storage.repository.model.Mileage
import ua.tarch64.composeQuery.Mutation
import ua.tarch64.composeQuery.mutation.MutationContent

data class CarCreateMutationInput(
    val name: String,
    val color: Color,
    val mileage: Mileage
)

@Composable
fun CarCreateMutation(content: MutationContent<CarCreateMutationInput, Car>) {
    val carRepository = koinInject<CarRepository>()

    Mutation(
        mutate = {
            carRepository.create(
                name = it.name,
                color = it.color,
                mileage = it.mileage
            )
        },

        onSuccess = { _, context ->
            context.invalidateQuery(CarQueryKeys.car)
        },

        content = content
    )
}
