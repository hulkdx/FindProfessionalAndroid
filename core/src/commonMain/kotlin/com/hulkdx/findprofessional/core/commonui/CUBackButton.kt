package com.hulkdx.findprofessional.core.commonui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.commonui.navbar.getNavigator

const val BACK_BUTTON_INNER_PADDING = 8
const val BACK_BUTTON_OUTER_PADDING = 16
const val BACK_BUTTON_HEIGHT = ((BACK_BUTTON_INNER_PADDING + BACK_BUTTON_OUTER_PADDING) * 2) + 16

@Composable
fun CUBackButton(
    modifier: Modifier = Modifier,
) {
    val navigator = getNavigator()
    CUBackButton(
        modifier = modifier,
        onClick = navigator::goBack
    )
}

@Composable
fun CUBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val iconColor = if (isPressed) Color.Blue else LocalContentColor.current

    Image(
        modifier = modifier
            .padding(BACK_BUTTON_OUTER_PADDING.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick,
            )
            .padding(BACK_BUTTON_INNER_PADDING.dp),
        colorFilter = ColorFilter.tint(iconColor),
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Back",
    )
}
