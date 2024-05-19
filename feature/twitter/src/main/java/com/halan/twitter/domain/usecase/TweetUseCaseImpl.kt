package com.halan.twitter.domain.usecase

import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import com.halan.twitter.domain.repository.TwitterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class TweetUseCaseImpl @Inject constructor(
    private val repository: TwitterRepository
) : TweetUseCase {
    override suspend fun postTweet(text: String, accessToken: String): Flow<TweetResponse> = repository.postTweet(text,accessToken)
    override suspend fun getAccessToken(): Flow<TwitterAccessTokenResponse> = repository.getAccessToken()
}
