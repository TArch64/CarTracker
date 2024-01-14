package screens.events.list.part

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.icons.TablerIcons
import compose.icons.tablericons.Car

@Composable
fun EventsCar(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Icon(TablerIcons.Car, contentDescription = "")
    }
}
