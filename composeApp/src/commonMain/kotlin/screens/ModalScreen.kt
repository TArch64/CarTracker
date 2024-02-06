package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowLeft

abstract class ModalScreen : AppScreen() {
    abstract val headerTitle: String

    @Composable
    override fun Content() {
        val navigator = getNavigator()

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            color = Color.Black,
                            strokeWidth = 2f
                        )
                    }
            ) {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(TablerIcons.ArrowLeft, contentDescription = "")
                }
                Text(headerTitle, style = MaterialTheme.typography.h6)
                Box(modifier = Modifier.size(48.dp))
            }
        }
    }

    @Composable
    abstract fun Body()
}
