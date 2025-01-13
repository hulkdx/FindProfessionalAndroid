@file:Suppress("FunctionName")

package com.hulkdx.findprofessional.feature.pro.availability.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.resources.Res
import com.hulkdx.findprofessional.core.resources.applyToAll
import com.hulkdx.findprofessional.core.resources.applyToOnly
import com.hulkdx.findprofessional.core.resources.checkout
import com.hulkdx.findprofessional.core.resources.editAvailableTime
import com.hulkdx.findprofessional.core.ui.commonui.CUBackButton
import com.hulkdx.findprofessional.core.ui.commonui.CUFilledButton
import com.hulkdx.findprofessional.core.ui.commonui.CUSnackBar
import com.hulkdx.findprofessional.core.ui.commonui.CUTextButton
import com.hulkdx.findprofessional.core.ui.theme.h2
import com.hulkdx.findprofessional.core.ui.theme.h3
import com.hulkdx.findprofessional.core.utils.koinViewModel
import com.hulkdx.findprofessional.feature.pro.availability.detail.ProAvailabilityDetailViewModel.UiState
import com.hulkdx.findprofessional.feature.pro.availability.detail.components.TimeSlotSection
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProAvailabilityDetailScreen(
    selectedDate: LocalDate,
    selectedTimeSlot: List<TimeSlot> = emptyList(), // TODO: get it from previous screen
    viewModel: ProAvailabilityDetailViewModel = koinViewModel(selectedDate, selectedTimeSlot),
) {
    val uiState by viewModel.uiState.collectAsState()
    val error by viewModel.error.collectAsState()

    ProAvailabilityDetailScreen(
        uiState = uiState,
        onDeleteClicked = viewModel::onDeleteClicked,
        onFromSelected = viewModel::onFromSelected,
        onToSelected = viewModel::onToSelected,
        onAddNewTimeSlotClicked = viewModel::onAddNewTimeSlotClicked,
        onApplyClicked = viewModel::onApplyClicked,
        onApplyToAllClicked = viewModel::onApplyToAllClicked,
        error = error?.localized(),
        onErrorDismissed = { viewModel.setError(null) },
    )
}

@Composable
fun ProAvailabilityDetailScreen(
    uiState: UiState,
    error: String?,
    onDeleteClicked: (Int) -> Unit,
    onFromSelected: (Int, String) -> Unit,
    onToSelected: (Int, String) -> Unit,
    onAddNewTimeSlotClicked: () -> Unit,
    onApplyClicked: () -> Unit,
    onApplyToAllClicked: () -> Unit,
    onErrorDismissed: () -> Unit,
) {
    Box {
        LazyColumn(
            modifier = Modifier
                .testTag("ProAvailabilityDetailScreen")
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .systemBarsPadding()
        ) {
            item { Title() }
            item { TitleDate(uiState.currentDate) }
            TimeSlotSection(
                uiState.timeSlots,
                uiState.availableTime,
                onDeleteClicked,
                onFromSelected,
                onToSelected,
                onAddNewTimeSlotClicked,
            )
        }
        CUBackButton(modifier = Modifier.align(Alignment.TopStart))
        Bottom(
            modifier = Modifier.align(Alignment.BottomStart),
            uiState,
            onApplyClicked,
            onApplyToAllClicked,
        )
        CUSnackBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            message = error,
            onDismiss = onErrorDismissed
        )
    }
}

@Composable
private fun Title() {
    Text(
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(Res.string.editAvailableTime),
        style = h2,
    )
}

@Composable
private fun TitleDate(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
        textAlign = TextAlign.Center,
        text = text,
        style = h3,
    )
}

@Composable
private fun Bottom(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onApplyClicked: () -> Unit,
    onApplyToAllClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 26.dp, vertical = 22.dp),
    ) {
        ApplyButton(uiState.applyButtonText, onApplyClicked)
        ApplyToAllButton(uiState.dayOfWeek ,onApplyToAllClicked)
    }
}

@Composable
private fun ApplyButton(applyButtonText: String, onCLick: () -> Unit) {
    CUFilledButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.applyToOnly, applyButtonText),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        contentPadding = PaddingValues(0.dp),
        onClick = onCLick,
    )
}

@Composable
private fun ApplyToAllButton(dayOfWeek: String, onCLick: () -> Unit) {
    CUFilledButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.applyToAll, dayOfWeek),
        onClick = onCLick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
        contentPadding = PaddingValues(0.dp),
    )
}
