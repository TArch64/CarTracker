package base.form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ua.tarch64.formify.control.FormFieldControl
import ua.tarch64.formify.ui.FormField

@Composable
private fun FormColorSwatch(color: Color, active: Boolean, onSelect: () -> Unit) {
    val cornerSize = CornerSize(8.dp)
    val shape = RoundedCornerShape(cornerSize)

    Box(
        modifier = Modifier
            .size(32.dp)
            .clickable { if (!active) onSelect() }
    ) {
        val density = LocalDensity.current

        Box(
            modifier = Modifier
                .matchParentSize()
                .drawBehind {
                    val strokeWidth = 3f
                    drawRoundRect(
                        color = Color.Black.copy(alpha = 0.1f),
                        style = Stroke(width = strokeWidth),
                        cornerRadius = CornerRadius(cornerSize.toPx(Size.Zero, density)),
                        topLeft = Offset(-strokeWidth / 2, -strokeWidth / 2),
                        size = Size(size.width + strokeWidth, size.height + strokeWidth)
                    )
                }
                .clip(shape)
                .background(color)
        )

        if (active) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape)
                    .background(Color.Black.copy(0.1f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Check,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.scale(0.9f)
                )
            }
        }
    }
}

@Composable
fun FormColorSwatches(
    label: String,
    field: FormFieldControl<Color>,
    options: List<Color>,
    modifier: Modifier = Modifier
) {
    FormField(field = field) { context ->
        Column(modifier = Modifier.fillMaxWidth().then(modifier)) {
            FormLabel(label, modifier = Modifier.padding(bottom = 8.dp))

            @OptIn(ExperimentalLayoutApi::class)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (option in options) {
                    FormColorSwatch(
                        color = option,
                        active = option == field.value,
                        onSelect = { context.setValue(option) }
                    )
                }
            }
        }
    }
}
