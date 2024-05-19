package com.halan.twitter.domain.repository

import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import kotlinx.coroutines.flow.Flow

interface TwitterRepository {

    suspend fun postTweet(text: String, accessToken: String): Flow<TweetResponse>
    suspend fun getAccessToken(): Flow<TwitterAccessTokenResponse>
}
