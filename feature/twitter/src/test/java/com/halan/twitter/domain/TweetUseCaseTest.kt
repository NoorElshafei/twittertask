package com.halan.twitter.domain


import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import com.halan.twitter.data.utils.Mocks
import com.halan.twitter.domain.repository.TwitterRepository
import com.halan.twitter.domain.usecase.TweetUseCase
import com.halan.twitter.domain.usecase.TweetUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import io.mockk.unmockkAll
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TweetUseCaseTest {

    private lateinit var useCase: TweetUseCase

    private val repository: TwitterRepository = mockkClass(TwitterRepository::class)

    private val expectedAuthSuccessResult = Mocks.responseAuth
    private val expectedAuthFailureResult = Mocks.exceptionAuth
    private val expectedPostTweetSuccessResult = Mocks.responsePostTweet
    private val expectedPostTweetFailureResult = Mocks.exceptionPostTweet

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = TweetUseCaseImpl(repository)
    }

    @Test
    fun `test auth with success response then return success`() = runTest {
        coEvery { repository.getAccessToken() } answers { flow { emit(expectedAuthSuccessResult) } }

        val response = useCase.getAccessToken()

        var success: TwitterAccessTokenResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedAuthSuccessResult, success!!)
        coVerify(exactly = 1) { repository.getAccessToken() }
    }

    @Test
    fun `test auth with failure response then return error`() = runTest {
        coEvery { repository.getAccessToken() } answers { flow { throw expectedAuthFailureResult } }
        val response = useCase.getAccessToken()

        var success: TwitterAccessTokenResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedAuthFailureResult.message, error!!.message)
        coVerify(exactly = 1) { repository.getAccessToken() }
    }

    @Test
    fun `test post tweet with success response then return success`() = runTest {
        coEvery { repository.postTweet(Mocks.text,Mocks.accessToken) } answers { flow { emit(expectedPostTweetSuccessResult) } }

        val response = useCase.postTweet(Mocks.text,Mocks.accessToken)

        var success: TweetResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(success)
        assertNull(error)
        assertEquals(expectedPostTweetSuccessResult, success!!)
        coVerify(exactly = 1) { repository.postTweet(Mocks.text,Mocks.accessToken) }
    }

    @Test
    fun `test post tweet with failure response then return error`() = runTest {
        coEvery { repository.postTweet(Mocks.text,Mocks.accessToken) } answers { flow { throw expectedPostTweetFailureResult } }
        val response = useCase.postTweet(Mocks.text,Mocks.accessToken)

        var success: TweetResponse? = null
        var error: Throwable? = null
        response
            .catch { error = it }
            .collect { success = it }

        assertNotNull(error)
        assertNull(success)
        assertEquals(expectedPostTweetFailureResult.message, error!!.message)
        coVerify(exactly = 1) { repository.postTweet(Mocks.text,Mocks.accessToken) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
