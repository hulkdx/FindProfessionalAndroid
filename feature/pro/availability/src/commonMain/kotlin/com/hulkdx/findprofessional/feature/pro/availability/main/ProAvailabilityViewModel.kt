package com.hulkdx.findprofessional.feature.pro.availability.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hulkdx.findprofessional.core.features.pro.usecase.GetAvailabilityUseCase
import com.hulkdx.findprofessional.core.utils.StringOrRes
import com.hulkdx.findprofessional.core.utils.generalError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class ProAvailabilityViewModel(
    getAvailabilityUseCase: GetAvailabilityUseCase,
): ViewModel() {
    val availabilities = flow { emit(getAvailabilityUseCase.execute()) }
        .catch { setError(it.generalError()) }
        .map { list -> list.map { it.date } }
        .stateIn(viewModelScope, WhileSubscribed(5_000), emptyList())

    private val _error = MutableStateFlow<StringOrRes?>(null)
    val error = _error.asStateFlow()

    fun onDateClicked(date: LocalDate) {
    }

    fun setError(error: StringOrRes?) = _error.update { error }
}
