package com.halan.twitter.domain.repository

import com.halan.core.utils.BuildConfig.CONSUMER_KEY
import com.halan.core.utils.BuildConfig.CONSUMER_SECRET
import com.halan.network.di.IoDispatcher
import com.halan.twitter.data.data_source.TwitterService
import com.halan.twitter.data.request.TweetBody
import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import com.halan.utils.config.EnvironmentConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TwitterRepositoryImpl @Inject constructor(
    private val apiService: TwitterService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val environmentConfig: EnvironmentConfig
) : TwitterRepository {

    override suspend fun postTweet(text: String, accessToken: String): Flow<TweetResponse> = flow {
        val response = apiService.postTweet(TweetBody(text), accessToken)
        emit(response)
    }.flowOn(dispatcher)

    override suspend fun getAccessToken(): Flow<TwitterAccessTokenResponse> = flow {
        val response = apiService.getAccessToken(
            "Basic " + android.util.Base64.encodeToString(
                "${environmentConfig.getConsumerKey()}:${environmentConfig.getConsumerSecret()}".toByteArray(),
                android.util.Base64.NO_WRAP
            ),
            "client_credentials"
        )
        emit(response)
    }.flowOn(dispatcher)
}