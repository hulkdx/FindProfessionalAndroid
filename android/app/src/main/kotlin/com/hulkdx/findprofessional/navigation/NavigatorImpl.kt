package com.hulkdx.findprofessional.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import com.hulkdx.findprofessional.common.navigation.NavigationScreen
import com.hulkdx.findprofessional.common.navigation.Navigator
import com.hulkdx.findprofessional.core.navigation.AndroidNavigationScreen
import com.hulkdx.findprofessional.feature.authentication.login.LoginNavigationScreen
import com.hulkdx.findprofessional.feature.authentication.signup.MainNavigationScreen
import com.hulkdx.findprofessional.feature.authentication.signup.SignUpNavigationScreen

class NavigatorImpl : Navigator {

    val screenState = mutableStateOf<AndroidNavigationScreen?>(null, neverEqualPolicy())

    override fun navigate(screen: NavigationScreen) {
        val value = when (screen) {
            NavigationScreen.Login -> LoginNavigationScreen()
            NavigationScreen.Main -> MainNavigationScreen()
            NavigationScreen.SignUp -> SignUpNavigationScreen()
        }
        screenState.value = value
    }
}