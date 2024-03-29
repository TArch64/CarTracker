package base.form

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormError(text: String) {
    Text(text,
        color = MaterialTheme.colors.error,
        modifier = Modifier.padding(top = 4.dp)
    )
}
