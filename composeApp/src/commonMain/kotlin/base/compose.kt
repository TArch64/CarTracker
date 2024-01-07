package base

import androidx.compose.ui.Modifier

fun Modifier.applyIf(toApply: Boolean, execute: Modifier.() -> Modifier): Modifier {
    return if (toApply) execute.invoke(this) else this
}
