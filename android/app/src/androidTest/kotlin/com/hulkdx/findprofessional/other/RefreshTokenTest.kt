@file:OptIn(ExperimentalCoroutinesApi::class)

package com.hulkdx.findprofessional.other

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.hulkdx.findprofessional.MainActivity
import com.hulkdx.findprofessional.common.config.api.interceptor.AppInterceptor
import com.hulkdx.findprofessional.common.config.storage.AccessTokenStorage
import com.hulkdx.findprofessional.common.feature.authentication.login.AuthToken
import com.hulkdx.findprofessional.common.feature.authentication.login.LoginApi
import com.hulkdx.findprofessional.common.feature.authentication.login.RefreshTokenApi
import com.hulkdx.findprofessional.common.feature.authentication.signup.model.AuthRequest
import com.hulkdx.findprofessional.ui.screen.login.launchLoginScreen
import com.hulkdx.findprofessional.utils.UiTestRule
import com.hulkdx.findprofessional.utils.get
import com.hulkdx.findprofessional.utils.getAll
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module

class RefreshTokenTest {

    companion object {
        private val INVALID_TOKENS = AuthToken("invalid_irrelevant_at", "invalid_irrelevant_rt")
        private val VALID_TOKENS = AuthToken("valid_irrelevant_at", "valid_irrelevant_rt")
    }

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 1)
    val rule = UiTestRule(composeRule)

    private lateinit var randomApi: RandomTestRefreshTokenApi
    private val accessTokenStorage: AccessTokenStorage = get()
    private val loginApi = LoginApiMock()
    private val refreshApi = RefreshApiMock()

    private val module = module {
        single { refreshApi } bind RefreshTokenApi::class
        single { loginApi } bind LoginApi::class
    }

    @Before
    fun setUp() {
        loadKoinModules(module)
        randomApi = RandomTestRefreshTokenApi(get(), getAll(), accessTokenStorage)
    }

    @After
    fun tearDown() {
        unloadKoinModules(module)
    }

    @Test
    fun when_invalid_access_token_should_intercept_refreshToken() = runTest {
        // Arrange
        val email = "test@email.com"
        val password = "somepass"
        // 0. refresh api response a valid token
        refreshApi.response = VALID_TOKENS

        // 1. we login with valid tokes first
        loginApi.response = VALID_TOKENS
        launchLoginScreen(composeRule) {
            typeEmail(email)
            typePassword(password)
            pressSignInButton()
        }.verify {
            mainScreenShown()
        }

        // Act
        // 2. then we call randomApi with invalid token
        accessTokenStorage.set(INVALID_TOKENS.accessToken)
        randomApi.randomApi()

        // Asserts
        assertThat(refreshApi.isRefreshTokenCalled, `is`(true))
        // assert no exceptions
    }

    // region mock classes

    private class LoginApiMock : LoginApi {
        var response: AuthToken? = null

        override suspend fun login(request: AuthRequest): AuthToken {
            return response!!
        }
    }

    private class RefreshApiMock : RefreshTokenApi {
        var response: AuthToken? = null
        var isRefreshTokenCalled = false

        override suspend fun refreshToken(refreshToken: String, accessToken: String): AuthToken {
            isRefreshTokenCalled = true
            return response!!
        }
    }

    private class RandomTestRefreshTokenApi(
        private val config: HttpClientConfig<*>.() -> Unit,
        private val interceptors: List<AppInterceptor>,
        private val accessTokenStorage: AccessTokenStorage,
    ) {
        suspend fun randomApi(): String {
            return httpClient().post {
                url("irrelevant")
                header(HttpHeaders.Authorization, "Bearer ${accessTokenStorage.get()}")
            }.body()
        }

        private fun httpClient(): HttpClient {
            val mockEngine = MockEngine { request ->
                println("Saba mockEngine, ${request.headers}")
                respond(
                    content = ByteReadChannel(""),
                    status = if (request.hasValidAccessToken()) HttpStatusCode.OK else HttpStatusCode.Unauthorized,
                    headers = headersOf()
                )
            }
            val client = HttpClient(mockEngine, config).apply {
                plugin(HttpSend).apply {
                    for (interceptor in interceptors) {
                        intercept(interceptor::intercept)
                    }
                }
            }
            return client
        }

        private fun HttpRequestData.getAccessToken(): String {
            return headers[HttpHeaders.Authorization]?.split("Bearer ")?.getOrNull(1) ?: ""
        }

        private fun HttpRequestData.hasValidAccessToken(): Boolean {
            return getAccessToken() != INVALID_TOKENS.accessToken
        }
    }

    // endregion
}
