package base.form

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormLabel(text: String, modifier: Modifier = Modifier) {
    Text(text, style = MaterialTheme.typography.caption, modifier = modifier)
}
