package com.hulkdx.findprofessional.core.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import org.koin.core.parameter.parametersOf

@Composable
inline fun <reified T : ViewModel> koinViewModel(vararg parameters: Any?): T {
    return org.koin.compose.viewmodel.koinViewModel {
        parametersOf(*parameters)
    }
}
