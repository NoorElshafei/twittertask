package com.halan.twitter.presentation

import app.cash.turbine.test
import com.halan.utils.states.DataState
import com.halan.twitter.data.utils.Mocks
import com.halan.twitter.domain.usecase.TweetUseCase
import com.halan.twitter.presentation.post_tweet.PostTweetViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private var tweetUseCase: TweetUseCase = mockkClass(TweetUseCase::class)

    private val expectedAccessTokenSuccessResult = Mocks.responseAuth
    private val expectedPostTweetSuccessResult = Mocks.responsePostTweet
    private val expectedAuthFailureResult = Mocks.exceptionAuth
    private val expectedPostTweetFailureResult = Mocks.exceptionPostTweet


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test auth with success response then return success`() {
        runTest {
            coEvery { tweetUseCase.getAccessToken() } answers { flow { emit(expectedAccessTokenSuccessResult) } }
            val viewModel = PostTweetViewModel(tweetUseCase)
            viewModel.authenticate(Mocks.text)
            viewModel.postTweetDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Success(Mocks.responseAuth), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) { tweetUseCase.getAccessToken() }
        }
    }

    @Test
    fun `test post tweet with success response then return success`() {
        runTest {
            coEvery { tweetUseCase.postTweet(Mocks.text,Mocks.accessToken) } answers { flow { emit(expectedPostTweetSuccessResult) } }
            val viewModel = PostTweetViewModel(tweetUseCase)
            viewModel.postTweet(Mocks.text,Mocks.accessToken)
            viewModel.postTweetDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Success(Mocks.responsePostTweet), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { tweetUseCase.postTweet(Mocks.text,Mocks.accessToken) }
        }
    }

    @Test
    fun `test auth with failure response then return error`() {
        runTest {
            coEvery { tweetUseCase.getAccessToken() } answers { flow { throw expectedAuthFailureResult } }
            val viewModel = PostTweetViewModel(tweetUseCase)
            viewModel.authenticate(Mocks.text)
            viewModel.authDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Failure(expectedAuthFailureResult), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { tweetUseCase.getAccessToken() }
        }
    }

    @Test
    fun `test post tweet with failure response then return error`() {
        runTest {
            coEvery { tweetUseCase.postTweet(Mocks.text,Mocks.accessToken) } answers { flow { throw expectedPostTweetFailureResult } }
            val viewModel = PostTweetViewModel(tweetUseCase)
            viewModel.postTweet(Mocks.text,Mocks.accessToken)
            viewModel.postTweetDataState.test {
                assertEquals(DataState.Loading, awaitItem())
                assertEquals(DataState.Failure(expectedPostTweetFailureResult), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { tweetUseCase.postTweet(Mocks.text,Mocks.accessToken) }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}
