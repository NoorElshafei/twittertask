package com.halan.twitter.data.repository


import com.halan.twitter.data.data_source.TwitterService
import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import com.halan.twitter.data.utils.Mocks
import com.halan.twitter.domain.repository.TwitterRepository
import com.halan.twitter.domain.repository.TwitterRepositoryImpl
import com.halan.utils.config.EnvironmentConfig
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import io.mockk.unmockkAll
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()
    private val environmentConfig = mockkClass(EnvironmentConfig::class)

    private lateinit var repository: TwitterRepository

    private val dataSourceTwitter: TwitterService = mockkClass(TwitterService::class)

    private val expectedAuthSuccessResult = Mocks.responseAuth
    private val expectedPostTweetSuccessResult = Mocks.responsePostTweet
    private val expectedAuthFailureResult = Mocks.exceptionAuth
    private val expectedPostTweetFailureResult = Mocks.exceptionPostTweet

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        repository = TwitterRepositoryImpl(dataSourceTwitter, testDispatcher,environmentConfig)
    }

    @Test
    fun `test auth with success response then return success`() = runTest {
        coEvery { dataSourceTwitter.getAccessToken(any(),any()) } returns expectedAuthSuccessResult

        val response = repository.getAccessToken()

        var success: TwitterAccessTokenResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedAuthSuccessResult, success!!)
        coVerify(exactly = 1) { dataSourceTwitter.getAccessToken(Mocks.auth,Mocks.grantType) }
    }

    @Test
    fun `test post tweet with success response then return success`() = runTest {
        coEvery { dataSourceTwitter.postTweet(
            any(),
            any()
        ) } returns expectedPostTweetSuccessResult

        val response = repository.postTweet(Mocks.text,Mocks.accessToken)

        var success: TweetResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedPostTweetSuccessResult, success!!)
        coVerify(exactly = 1) { dataSourceTwitter.postTweet(
            any(),
            any()
        ) }
    }

    @Test
    fun `test auth with failure response then return error`() = runTest {
        coEvery { dataSourceTwitter.getAccessToken(Mocks.auth,Mocks.grantType) } throws expectedAuthFailureResult

        val response = repository.getAccessToken()

        var success: TwitterAccessTokenResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedAuthFailureResult.message, error!!.message)
        coVerify(exactly = 1) { dataSourceTwitter.getAccessToken(Mocks.auth,Mocks.grantType) }
    }

    @Test
    fun `test post tweet with failure response then return error`() = runTest {
        coEvery { dataSourceTwitter.postTweet(
            any(),
            any()
        ) } throws expectedPostTweetFailureResult

        val response = repository.postTweet(Mocks.text,Mocks.accessToken)

        var success: TweetResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedPostTweetFailureResult.message, error!!.message)
        coVerify(exactly = 1) { dataSourceTwitter.postTweet(
            any(),
            any()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
