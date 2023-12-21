package com.hulkdx.findprofessional.feature.home.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hulkdx.findprofessional.common.feature.home.HomeDetailUseCase
import com.hulkdx.findprofessional.common.feature.home.model.Professional
import com.hulkdx.findprofessional.core.utils.getStateFlow
import com.hulkdx.findprofessional.feature.home.detail.HomeDetailNavigationScreen.Companion.ARG1
import com.hulkdx.findprofessional.feature.home.detail.utils.createAvailabilityData
import dev.icerock.moko.resources.desc.StringDesc


class HomeDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: HomeDetailUseCase,
) : ViewModel() {

    val professional = savedStateHandle.getStateFlow<Professional>(ARG1)
    val error = savedStateHandle.getStateFlow<StringDesc?>("error", null)

    val availability = createAvailabilityData()

    fun onBookClick() {
    }

    fun setError(error: StringDesc?) {
        savedStateHandle["error"] = error
    }

    fun onReviewShowMoreClicked() {
    }
}
