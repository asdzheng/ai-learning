package com.figma.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt

/**
 * Custom modifiers that mimic Figma's fill behavior in Jetpack Compose.
 * These modifiers will only take up remaining space without affecting peer elements,
 * similar to Figma's fill width/height functionality.
 */
object FigmaFillModifier {
    /**
     * Fills remaining width in the parent container without affecting peer elements
     * @return Modifier that takes remaining horizontal space
     */
    fun Modifier.fillRemainingWidth() = layout { measurable, constraints ->
        // Calculate remaining width after measuring the content
        val placeable = measurable.measure(
            constraints.copy(maxWidth = Constraints.Infinity)
        )
        val remainingWidth = constraints.maxWidth - constraints.minWidth
        
        // Use remaining width if available, otherwise use content width
        val width = if (remainingWidth > 0) remainingWidth else placeable.width
        
        layout(width, placeable.height) {
            placeable.place(0, 0)
        }
    }

    /**
     * Fills remaining height in the parent container without affecting peer elements
     * @return Modifier that takes remaining vertical space
     */
    fun Modifier.fillRemainingHeight() = layout { measurable, constraints ->
        // Calculate remaining height after measuring the content
        val placeable = measurable.measure(
            constraints.copy(maxHeight = Constraints.Infinity)
        )
        val remainingHeight = constraints.maxHeight - constraints.minHeight
        
        // Use remaining height if available, otherwise use content height
        val height = if (remainingHeight > 0) remainingHeight else placeable.height
        
        layout(placeable.width, height) {
            placeable.place(0, 0)
        }
    }

    /**
     * Fills remaining space in both directions without affecting peer elements
     * @return Modifier that takes remaining space in both dimensions
     */
    fun Modifier.fillRemainingSize() = this.fillRemainingWidth().fillRemainingHeight()

    /**
     * Fills remaining width with a specific fraction of the available space
     * @param fraction The fraction of remaining space to fill (0.0 to 1.0)
     * @return Modifier that takes the specified fraction of remaining horizontal space
     */
    fun Modifier.fillRemainingWidth(fraction: Float) = layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints.copy(maxWidth = Constraints.Infinity)
        )
        val remainingWidth = constraints.maxWidth - constraints.minWidth
        
        val width = if (remainingWidth > 0) {
            (remainingWidth * fraction.coerceIn(0f, 1f)).roundToInt()
        } else placeable.width
        
        layout(width, placeable.height) {
            placeable.place(0, 0)
        }
    }

    /**
     * Fills remaining height with a specific fraction of the available space
     * @param fraction The fraction of remaining space to fill (0.0 to 1.0)
     * @return Modifier that takes the specified fraction of remaining vertical space
     */
    fun Modifier.fillRemainingHeight(fraction: Float) = layout { measurable, constraints ->
        val placeable = measurable.measure(
            constraints.copy(maxHeight = Constraints.Infinity)
        )
        val remainingHeight = constraints.maxHeight - constraints.minHeight
        
        val height = if (remainingHeight > 0) {
            (remainingHeight * fraction.coerceIn(0f, 1f)).roundToInt()
        } else placeable.height
        
        layout(placeable.width, height) {
            placeable.place(0, 0)
        }
    }

    /**
     * Fills remaining space in both directions with specific fractions
     * @param widthFraction The fraction of remaining width to fill (0.0 to 1.0)
     * @param heightFraction The fraction of remaining height to fill (0.0 to 1.0)
     * @return Modifier that takes the specified fractions of remaining space in both dimensions
     */
    fun Modifier.fillRemainingSize(
        widthFraction: Float,
        heightFraction: Float
    ) = this.fillRemainingWidth(widthFraction).fillRemainingHeight(heightFraction)
}