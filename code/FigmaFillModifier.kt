package com.figma.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize

fun Modifier.fillRemainingWidth(): Modifier = this then FillRemainingWidthModifier()

fun Modifier.fillRemainingHeight(): Modifier = this then FillRemainingHeightModifier()

fun Modifier.fillRemainingSize(): Modifier = this then FillRemainingSizeModifier()

private class FillRemainingWidthModifier : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(
            // We are setting maxWidth to the incoming maxWidth, but allowing minWidth to be 0
            // This allows the content to be smaller if it wants, but it can expand to maxWidth
            constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth)
        )
        // Now, we use constraints.maxWidth as the layout width, effectively filling the available width
        val layoutWidth = constraints.maxWidth
        return layout(layoutWidth, placeable.height) { // Use maxWidth for width, placeable.height for actual content height
            placeable.placeRelative(0, 0)
        }
    }
}

private class FillRemainingHeightModifier : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(
            // We are setting maxHeight to the incoming maxHeight, but allowing minHeight to be 0
            // This allows the content to be smaller if it wants, but it can expand to maxHeight
            constraints.copy(minHeight = 0, maxHeight = constraints.maxHeight)
        )
        // Now, we use constraints.maxHeight as the layout height, effectively filling the available height
        val layoutHeight = constraints.maxHeight
        return layout(placeable.width, layoutHeight) { // Use placeable.width for actual content width, maxHeight for height
            placeable.placeRelative(0, 0)
        }
    }
}

private class FillRemainingSizeModifier : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(
            // We are setting maxWidth and maxHeight to the incoming maxWidth and maxHeight,
            // but allowing minWidth and minHeight to be 0.
            // This allows the content to be smaller if it wants, but it can expand to maxWidth/maxHeight
            constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth, minHeight = 0, maxHeight = constraints.maxHeight)
        )
        // Now, we use constraints.maxWidth and constraints.maxHeight as the layout size, effectively filling the available space
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        return layout(layoutWidth, layoutHeight) { // Use maxWidth and maxHeight for layout size
            placeable.placeRelative(0, 0)
        }
    }
}
