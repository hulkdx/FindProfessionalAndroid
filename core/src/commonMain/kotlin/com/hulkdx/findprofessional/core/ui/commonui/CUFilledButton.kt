package com.hulkdx.findprofessional.core.ui.commonui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.ui.theme.AppTheme
import com.hulkdx.findprofessional.core.ui.theme.body1Medium
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CUFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        contentPadding = contentPadding,
        onClick = onClick,
        colors = colors,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = text,
            style = body1Medium,
        )
    }
}

@Preview
@Composable
private fun CUFilledButtonPreview() {
    AppTheme {
        CUFilledButton(
            text = "Button"
        ) {}
    }
}
