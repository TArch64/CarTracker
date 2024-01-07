package storage.database.columnAdapters

import androidx.compose.ui.graphics.Color
import app.cash.sqldelight.ColumnAdapter
import com.github.ajalt.colormath.extensions.android.composecolor.toColormathColor
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.model.RGB

class ColorColumnAdapter: ColumnAdapter<Color, String> {
    override fun decode(databaseValue: String) = RGB(databaseValue).toComposeColor()
    override fun encode(value: Color) = value.toColormathColor().toSRGB().toHex()
}
