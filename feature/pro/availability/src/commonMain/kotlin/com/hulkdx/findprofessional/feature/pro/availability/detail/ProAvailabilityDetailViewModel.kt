package com.hulkdx.findprofessional.feature.pro.availability.detail

import androidx.lifecycle.ViewModel
import com.hulkdx.findprofessional.core.utils.StringOrRes
import com.hulkdx.findprofessional.core.utils.TimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class ProAvailabilityDetailViewModel(
    selectedDate: LocalDate,
    selectedTimeSlot: List<TimeSlot>,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.create(selectedDate, selectedTimeSlot))
    val uiState = _uiState.asStateFlow()

    private val _error = MutableStateFlow<StringOrRes?>(null)
    val error = _error.asStateFlow()

    fun onDeleteClicked(index: Int) {
        val timeSlots = uiState.value.timeSlots.toMutableList()
        timeSlots.removeAt(index)
        setTimeSlots(timeSlots)
    }

    fun onFromSelected(index: Int, time: String) {
        val timeSlots = uiState.value.timeSlots.toMutableList()
        timeSlots[index] = timeSlots[index].copy(from = time)
        setTimeSlots(timeSlots)
    }

    fun onToSelected(index: Int, time: String) {
        val timeSlots = uiState.value.timeSlots.toMutableList()
        timeSlots[index] = timeSlots[index].copy(to = time)
        setTimeSlots(timeSlots)
    }

    fun onAddNewTimeSlotClicked() {
        val timeSlots = uiState.value.timeSlots.toMutableList()
        setTimeSlots(timeSlots + TimeSlot("00:00", "00:00"))
    }

    fun onApplyClicked() {
        // TODO:
    }

    fun onApplyToAllClicked() {
        // TODO:
    }

    private fun setTimeSlots(timeSlots: List<TimeSlot>) {
        _uiState.update { it.copy(timeSlots = timeSlots) }
    }

    fun setError(error: StringOrRes?) = _error.update { error }

    data class UiState(
        val timeSlots: List<TimeSlot>,
        val availableTime: List<String>,
        val dayOfWeek: String,
        val applyButtonText: String,
        val currentDate: String,
    ) {
        companion object {
            fun create(
                selectedDate: LocalDate,
                selectedTimeSlot: List<TimeSlot>,
            ) = UiState(
                timeSlots = selectedTimeSlot,
                availableTime = (0..<24 * 60 step 30).map { TimeUtils.formattedTime(it) },
                dayOfWeek = selectedDate.dayOfWeek.name
                    .lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                applyButtonText = run {
                    val month = selectedDate.month.name
                        .lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    val day = selectedDate.dayOfMonth
                    "$month $day"
                },
                currentDate = run {
                    val month = selectedDate.month.name
                        .lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    val day = selectedDate.dayOfMonth
                    val year = selectedDate.year
                    "$year $month $day"
                },
            )
        }
    }
}
