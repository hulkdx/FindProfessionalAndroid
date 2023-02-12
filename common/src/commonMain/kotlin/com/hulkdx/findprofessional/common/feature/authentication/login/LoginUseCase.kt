package com.hulkdx.findprofessional.common.feature.authentication.login

import com.hulkdx.findprofessional.common.feature.authentication.signup.model.AuthRequest
import com.hulkdx.findprofessional.common.navigation.NavigationScreen
import com.hulkdx.findprofessional.common.navigation.Navigator


class LoginUseCase(
    private val navigator: Navigator,
    private val api: LoginApi,
) {
    fun onSignUpClicked() {
        navigator.navigate(NavigationScreen.SignUp)
    }

    suspend fun onSignInClicked(request: AuthRequest) {
        api.login(request)
        navigator.navigate(NavigationScreen.Main)
    }
}
