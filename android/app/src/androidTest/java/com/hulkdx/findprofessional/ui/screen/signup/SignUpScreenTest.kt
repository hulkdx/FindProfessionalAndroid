package com.hulkdx.findprofessional.ui.screen.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.hulkdx.findprofessional.MainActivity
import com.hulkdx.findprofessional.inMemoryModule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class SignUpScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        loadKoinModules(inMemoryModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(inMemoryModule)
    }

    @Test
    fun performSignUp() {
        launchSignUpScreen(composeRule) {
            pressSignUpButton()
        }.verify {
            mainScreenShown()
        }
    }
}
