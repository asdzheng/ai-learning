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
            // This means the composable will try to take up as much width as possible within the parent's constraints,
            // similar to Figma's "Fill Width" - it takes remaining space but doesn't force parent to expand.
            constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth)
        )
        return layout(placeable.width, placeable.height) {
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
            // This means the composable will try to take up as much height as possible within the parent's constraints,
            // similar to Figma's "Fill Height" - it takes remaining space but doesn't force parent to expand.
            constraints.copy(minHeight = 0, maxHeight = constraints.maxHeight)
        )
        return layout(placeable.width, placeable.height) {
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
            // This makes the composable fill both width and height within the parent's constraints,
            // similar to Figma's "Fill" for both directions.
            constraints.copy(minWidth = 0, maxWidth = constraints.maxWidth, minHeight = 0, maxHeight = constraints.maxHeight)
        )
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}
