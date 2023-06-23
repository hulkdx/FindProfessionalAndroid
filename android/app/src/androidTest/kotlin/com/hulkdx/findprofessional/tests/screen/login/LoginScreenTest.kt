package com.hulkdx.findprofessional.tests.screen.login

import com.hulkdx.findprofessional.common.config.api.InMemoryApi
import com.hulkdx.findprofessional.common.feature.authentication.signup.model.AuthRequest
import com.hulkdx.findprofessional.utils.ScreenTest
import org.junit.Test

class LoginScreenTest : ScreenTest() {

    @Test
    fun performSignUp() {
        launchLoginScreen(composeRule) {
            pressSignUpButton()
        }.verify {
            signupScreenShown()
        }
    }

    @Test
    fun performLogin() {
        InMemoryApi.user = AuthRequest("test@email.com", "somepass")

        launchLoginScreen(composeRule) {
            typeEmail("test@email.com")
            typePassword("somepass")
            pressSignInButton()
        }.verify {
            homeScreenShown()
        }
    }
}
