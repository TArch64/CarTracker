package screens.events.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import storage.repository.model.CarMileage

@Composable
fun EventsListPart(mileage: CarMileage) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Text(mileage.humanReadable)
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .drawBehind {
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    color = Color.Black,
                    strokeWidth = 12f
                )
                drawLine(
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    color = Color.Black,
                    strokeWidth = 12f
                )
            }
        )
    }
}
