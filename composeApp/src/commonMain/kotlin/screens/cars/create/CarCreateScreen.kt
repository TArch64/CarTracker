package screens.cars.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import queries.car.CarCreateMutation
import queries.car.CarCreateMutationInput
import screens.AppScreen
import screens.SplashScreen
import storage.repository.model.CarMileage

class CarCreateScreen : AppScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text("No car created yet", style = MaterialTheme.typography.h4)

                Spacer(modifier = Modifier.height(32.dp))

                CarCreateMutation { mutation ->
                    CarCreateForm { formObject ->
                        mutation.mutate(
                            CarCreateMutationInput(
                                name = formObject.getField<String>("name").value,
                                color = formObject.getField<Color>("color").value,
                                mileage = CarMileage(formObject.getField<Int>("mileage").value)
                            )
                        )
                        navigator.replace(SplashScreen())
                    }
                }
            }
        }
    }
}
