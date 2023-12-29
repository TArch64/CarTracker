package screens.cars.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import screens.AppScreen

class CarCreateScreen : AppScreen() {
    @Composable
    override fun Content() {
        val model = getScreenModel<CarCreateModel>()

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("No car created yet")

                Spacer(modifier = Modifier.height(16.dp))

                CarCreateForm {
                    println(it)
                    model.create()
                }
            }
        }
    }
}
