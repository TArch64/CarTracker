package screens.cars.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import screens.AppScreen

class CarCreateScreen : AppScreen() {
    @Composable
    override fun Content() {
        val model = getScreenModel<CarCreateModel>()

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text("No car created yet", style = MaterialTheme.typography.h4)

                Spacer(modifier = Modifier.height(32.dp))

                CarCreateForm { model.create(it) }
            }
        }
    }
}
