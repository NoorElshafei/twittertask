package com.halan.twitter.domain.usecase

import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Created_by: Noor Elshafei
 * @Date: 4/20/2024
 */


interface TweetUseCase {

    suspend fun postTweet(text: String, accessToken: String): Flow<TweetResponse>
    suspend fun getAccessToken(): Flow<TwitterAccessTokenResponse>

}