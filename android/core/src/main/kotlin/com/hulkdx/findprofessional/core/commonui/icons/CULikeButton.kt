package com.hulkdx.findprofessional.core.commonui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.R

@Composable
fun CULikeButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_like),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                if (isSelected) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onError
            )
        )
    }
}
