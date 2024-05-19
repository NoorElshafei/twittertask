package com.halan.twitter.data.data_source

import com.halan.twitter.data.request.TweetBody
import com.halan.twitter.data.response.TweetResponse
import com.halan.twitter.data.response.TwitterAccessTokenResponse
import com.halan.twitter.data.utils.EndPoints
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TwitterService {
    @Headers("Content-Type: application/json")
    @POST(EndPoints.TWEETS)
    suspend fun postTweet(
        @Body tweet: TweetBody,
        @Query("oauth_token") oauthToken: String
    ): TweetResponse

    @Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
    @POST(EndPoints.AUTH)
    suspend fun getAccessToken(
        @Query("Authorization") authorization: String,
        @Query("grant_type") grantType: String
    ): TwitterAccessTokenResponse
}
